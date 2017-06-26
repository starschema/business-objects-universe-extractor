package com.bot.botmeta.boextraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.bot.botmeta.BOMetaDataExtraction;
import com.bot.botmeta.bosemantic.semanticstructure.BOFolder;
import com.bot.botmeta.bosemantic.semanticstructure.BOMetaObjects;
import com.bot.botmeta.bosemantic.semanticstructure.Folders;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.sap.sl.sdk.authoring.businesslayer.Attribute;
import com.sap.sl.sdk.authoring.businesslayer.BlContainer;
import com.sap.sl.sdk.authoring.businesslayer.BlItem;
import com.sap.sl.sdk.authoring.businesslayer.BusinessLayerFactory;
import com.sap.sl.sdk.authoring.businesslayer.Dimension;
import com.sap.sl.sdk.authoring.businesslayer.Filter;
import com.sap.sl.sdk.authoring.businesslayer.Folder;
import com.sap.sl.sdk.authoring.businesslayer.ItemState;
import com.sap.sl.sdk.authoring.businesslayer.Measure;
import com.sap.sl.sdk.authoring.businesslayer.NativeRelationalFilter;
import com.sap.sl.sdk.authoring.businesslayer.RelationalBusinessLayer;
import com.sap.sl.sdk.authoring.businesslayer.RootFolder;
import com.sap.sl.sdk.authoring.businesslayer.internal.emf.impl.FilterImpl;
import com.sap.sl.sdk.authoring.cms.CmsResourceService;
import com.sap.sl.sdk.authoring.cms.DataFederatorService;
import com.sap.sl.sdk.authoring.datafoundation.DataFoundation;
import com.sap.sl.sdk.authoring.datafoundation.DataFoundationFactory;
import com.sap.sl.sdk.authoring.datafoundation.MonoSourceDataFoundation;
import com.sap.sl.sdk.authoring.local.LocalResourceService;
import com.sap.sl.sdk.framework.SlContext;
import com.sap.sl.sdk.framework.cms.CmsSessionService;

public class ExtractBOBJMetaData {

	public static DataFoundation _dataFoundation;
	public static BusinessLayerFactory _businessLayerFactory;
	public static DataFoundationFactory _dataFoundationFactory;
	public static DataFederatorService _dataFederatorService;
	public static LocalResourceService _localResourceService;
	public static CmsResourceService _cmsResourceService;
	public static SlContext _slContext;
	private static IEnterpriseSession enterpriseSession;

	List<Object> listFolder;
	List<Object> _listDimensions;
	List<Object> listDimension;
	List<Object> listAttribute;
	List<Object> listMeasure;
	List<Object> _listMeasures;
	List<Object> listFilter;
	List<Object> _listFilters;

	public static RelationalBusinessLayer businessLayer;
	static Logger log = LogManager.getLogger(ExtractBOBJMetaData.class);

	public static Map<String, String> dbConnectionMap = new HashMap<String, String>();
	public static List<String> tablesList = new ArrayList<String>();
	BOMetaObjects metadataObjects = new BOMetaObjects();

	public BOMetaObjects getBOBJData() {

		try {
			log.debug("In initialise");

			_slContext = SlContext.create();
			connectToBOBJ();

			_dataFoundationFactory = _slContext
					.getService(DataFoundationFactory.class);
			_dataFederatorService = _slContext
					.getService(DataFederatorService.class);
			_localResourceService = _slContext
					.getService(LocalResourceService.class);
			_cmsResourceService = _slContext
					.getService(CmsResourceService.class);
			_businessLayerFactory = _slContext
					.getService(BusinessLayerFactory.class);
			
			System.out.println("Extracting BO items.");

			extractSemanticLayer();			

			listFolder = new ArrayList<Object>();
			listDimension = new ArrayList<Object>();
			listAttribute = new ArrayList<Object>();
			listMeasure = new ArrayList<Object>();
			listFilter = new ArrayList<Object>();

			//Extract BO Items and process and save BOMetadata object
			getBOItemsAndProcess();
			
			System.out.println("BO items extraction completed.");


		} catch (SDKException e) {
			log.error("SDK Exception in intialization" + e);
			log.error(e.getMessage(), e);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in intialization" + e);
			log.error(e.getMessage(), e);
		}
		return metadataObjects;
	}

	private void connectToBOBJ() throws SDKException {
		System.out.println("Trying to establish BO connection...");
		String userName = BOMetaDataExtraction.botConfig.getBoServerLoginInfo()
				.getUserName();
		String server = BOMetaDataExtraction.botConfig.getBoServerLoginInfo()
				.getServerIP();
		String authentication = BOMetaDataExtraction.botConfig
				.getBoServerLoginInfo().getAuthentication();
		String password = BOMetaDataExtraction.botConfig.getBoServerLoginInfo()
				.getPassword();
		enterpriseSession = CrystalEnterprise.getSessionMgr().logon(userName,
				password, server, authentication);
		_slContext.getService(CmsSessionService.class).setSession(
				enterpriseSession);
		System.out.println("Connection successful.");
		log.debug("Connection successful");
	}

	private boolean getBOItemsAndProcess() {
		RootFolder rootFolder = businessLayer.getRootFolder();
		getBlModelRecurcively(rootFolder);
		processFolder(listFolder, metadataObjects);
		processDimension(listDimension, metadataObjects);
		processMeasure(listMeasure, metadataObjects);
		processFilters(listFilter, metadataObjects);
		return false;
	}

	private void getBlModelRecurcively(BlItem biRootFolder) {

		List<BlItem> kids = new ArrayList<BlItem>();
		if (biRootFolder instanceof Folder) {
			Folder rootFolder = (Folder) biRootFolder;
			listFolder.add(rootFolder);
			kids = rootFolder.getChildren();

		}

		if (kids != null && kids.size() != 0) {
			List<BlItem> classList = new ArrayList<BlItem>();
			List<BlItem> folders = getChildren(kids, classList);
			if (folders != null && folders.size() != 0) {
				for (BlItem folder : folders) {
					getBlModelRecurcively(folder);
				}
			}

		}
	}

	private List<BlItem> getChildren(List<BlItem> blItems,
			List<BlItem> classList) {

		if (blItems != null) {
			for (BlItem blItem : blItems) {

				// Extracting Dimensions
				if (blItem instanceof Dimension) {
					Dimension dimension = (Dimension) blItem;
					listDimension.add(dimension);
					List<BlItem> attributes = dimension.getChildren();
					for (BlItem biAttribute : attributes) {
						Attribute attribute = (Attribute) biAttribute;
						listAttribute.add(attribute);
					}
				}

				// Extracting Measures
				if (blItem instanceof Measure) {
					Measure measure = (Measure) blItem;
					listMeasure.add(measure);
				}

				// Extracting Filters
				if (blItem instanceof Filter) {
					FilterImpl filter = (FilterImpl) blItem;
					listFilter.add(filter);
				}

				classList.add(blItem);
			}
		} else
			return null;
		return classList;
	}

	private void extractSemanticLayer() {
		log.info("Retrieving Universe");
		String retrieveUniverse = "";
		try {
			String localBlxPath = BOMetaDataExtraction.botConfig
					.getLocalUniversePath();
			

			String repositoryFolder = _cmsResourceService.UNIVERSES_ROOT
					+ "/"
					+ BOMetaDataExtraction.botConfig.getUniverseExportFolder()
					+ "/"
					+ BOMetaDataExtraction.botConfig.getUniverseName();

			
			retrieveUniverse = _cmsResourceService.retrieveUniverse(
					repositoryFolder, localBlxPath, true);
			
			
		} catch (Exception e) {			
			log.error("Exception in retrieving Universe" + e);
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}

		businessLayer = (RelationalBusinessLayer) _localResourceService
				.load(retrieveUniverse);

		String dfxPath = businessLayer.getDataFoundationPath();
		
		_dataFoundation = (MonoSourceDataFoundation) _localResourceService
				.load(dfxPath);

	}

	private BOMetaObjects processFolder(List<Object> folders,
			BOMetaObjects metadataObjects) {
		try {

			
			
			log.debug("Class Transformation Started");
			BOFolder model = null;
			List<BOFolder> listModels = new ArrayList<BOFolder>();
			Folders models = new Folders();
			
			for (Object object : folders) {
				Folder rootFolder = (Folder) object;
				String rootFolderName = rootFolder.getName();
				if (rootFolderName != "") {
					BlContainer parent = rootFolder.getParent();
					String parentName = "";
					if (parent != null)
						parentName = parent.getName();
					model = new BOFolder();
					ItemState itemState = rootFolder.getState();
					StringBuilder path = new StringBuilder();
					path = getPath(parent, rootFolderName, path);

					model.setName(rootFolderName);
					if (parentName != "") {
						model.setParentName(parentName);
						model.setNamespace(parentName);
						if (path != null)
							model.setPath(new String(path));
					}					
					listModels.add(model);
				}
			}
			models.setFolders(listModels);
			metadataObjects.setFolders(models);
			

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error in folder extraction.");
		}

		return null;
	}

	private BOMetaObjects processDimension(List<Object> dimensions,
			BOMetaObjects metadataObjects) {
		try {
			
			
			_listDimensions = dimensions;
			log.debug("Dimension Process Started");
			com.bot.botmeta.bosemantic.semanticstructure.Dimension attribute = null;
			Folders models = metadataObjects.getFolders();
			List<BOFolder> listModels = models.getFolders();
			List<com.bot.botmeta.bosemantic.semanticstructure.Dimension> attributes;
			
			for (BOFolder model : listModels) {
				attributes = model.getAttributes();
				if (attributes == null)
					attributes = new ArrayList<com.bot.botmeta.bosemantic.semanticstructure.Dimension>();
				for (Object object : dimensions) {
					Dimension dimension = (Dimension) object;
					String parentName = "";
					BlContainer parent = dimension.getParent();
					if (parent != null)
						parentName = parent.getName();
					// to get the path as that of the model path
					parent = parent.getParent();

					StringBuilder path = new StringBuilder();
					path = getPath(parent, parentName, path);

					if (model.getPath() != null) {
						if ((model.getPath().equals(new String(path)) && model
								.getName().equals(parentName))) {
							attribute = extractDimension(dimension);
							attributes.add(attribute);
						

						}
					} else if (model.getName().equals(parentName)
							&& new String(path).equals("")) {
						attribute = extractDimension(dimension);
						attributes.add(attribute);					

					}
				}
				model.setAttributes(attributes);
				models.setFolders(listModels);
				metadataObjects.setFolders(models);
				
				
			}
		
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error in dimension extraction.");
		}
		// TODO Auto-generated method stub
		return null;
	}

	private BOMetaObjects processMeasure(List<Object> measures,
			BOMetaObjects metadataObjects) {
		try {
			
			log.debug("Measure Process Started");
			_listMeasures = measures;
			com.bot.botmeta.bosemantic.semanticstructure.Measure boMeasure;
			Folders models = metadataObjects.getFolders();
			List<BOFolder> listModels = models.getFolders();
			List<com.bot.botmeta.bosemantic.semanticstructure.Measure> boMeasures;
			for (BOFolder model : listModels) {
				boMeasures = model.getMeasures();
				if (boMeasures == null)
					boMeasures = new ArrayList<com.bot.botmeta.bosemantic.semanticstructure.Measure>();
				for (Object object : measures) {
					Measure measure = (Measure) object;
					String parentName = "";

					BlContainer parent = measure.getParent();
					if (parent != null)
						parentName = parent.getName();
					parent = parent.getParent();
					StringBuilder path = new StringBuilder();
					path = getPath(parent, parentName, path);

					if (model.getPath() != null) {
						if ((model.getPath().equals(new String(path)) && model
								.getName().equals(parentName))) {
							boMeasure = extractMeasure(measure);
							boMeasures.add(boMeasure);

						}
					} else if (model.getName().equals(parentName)
							&& new String(path).equals("")) {
						boMeasure = extractMeasure(measure);
						boMeasures.add(boMeasure);
					}
				}

				model.setMeasures(boMeasures);
				models.setFolders(listModels);
				metadataObjects.setFolders(models);
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error in metric extraction.");
		}
		return null;
	}

	private BOMetaObjects processFilters(List<Object> filters,
			BOMetaObjects metadataObjects) {
		// TODO Auto-generated method stub

		try {

			log.debug("Filter Process Started");
			_listFilters = filters;
			com.bot.botmeta.bosemantic.semanticstructure.Filter boFilter;
			Folders models = metadataObjects.getFolders();
			List<BOFolder> listModels = models.getFolders();
			List<com.bot.botmeta.bosemantic.semanticstructure.Filter> boFilters;
			int dimensionSuccessCount = 0;
			int dbTableFailCount = 0;
			for (BOFolder model : listModels) {
				boFilters = model.getFilters();
				if (boFilters == null)
					boFilters = new ArrayList<com.bot.botmeta.bosemantic.semanticstructure.Filter>();
				for (Object object : filters) {
					FilterImpl filter = (FilterImpl) object;
					String parentName = "";
					BlContainer parent = filter.getParent();
					if (parent != null)
						parentName = parent.getName();

					parent = parent.getParent();

					StringBuilder path = new StringBuilder();
					path = getPath(parent, parentName, path);
					if (model.getPath() != null) {
						if ((model.getPath().equals(new String(path)) && model
								.getName().equals(parentName))) {
							boFilter = extractFilter(filter);
							boFilters.add(boFilter);
							dimensionSuccessCount++;
						}
					} else if (model.getName().equals(parentName)
							&& new String(path).equals("")) {
						boFilter = extractFilter(filter);
						boFilters.add(boFilter);
						dimensionSuccessCount++;
					}
				}

				model.setFilters(boFilters);
				models.setFolders(listModels);
				metadataObjects.setFolders(models);
			}						
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private StringBuilder getPath(BlContainer parent, String rootFolderName,
			StringBuilder path) {
		if (parent != null
				&& !parent.getName().trim().equalsIgnoreCase(rootFolderName)) {

			if (parent != null) {
				path = getPath(parent.getParent(), rootFolderName, path);
				if (path.toString().equalsIgnoreCase("")) {
					path.append(parent.getName());
				} else {
					path.append("\\" + parent.getName());
				}
			}

		}

		return path;
	}

	private com.bot.botmeta.bosemantic.semanticstructure.Dimension extractDimension(
			Dimension dimension) {
		com.bot.botmeta.bosemantic.semanticstructure.Dimension attribute = new com.bot.botmeta.bosemantic.semanticstructure.Dimension();
		String attributeName = dimension.getName();
		String uid = dimension.getIdentifier();
		attribute.setId(uid);
		attribute.setName(attributeName);
		return attribute;
	}

	private com.bot.botmeta.bosemantic.semanticstructure.Measure extractMeasure(
			Measure measure) {
		com.bot.botmeta.bosemantic.semanticstructure.Measure boMeasure = new com.bot.botmeta.bosemantic.semanticstructure.Measure();
		// RelationalBinding relationalBinding = (RelationalBinding)
		// measure.getBinding();
		StringBuilder path = new StringBuilder();
		String rootName = measure.getName();
		BlContainer parent = measure.getParent();
		path = getPath(parent, rootName, path);
		String attributeName = measure.getName();
		String uid = measure.getIdentifier();
		boMeasure.setName(attributeName);
		boMeasure.setId(uid);
		return boMeasure;

	}

	private com.bot.botmeta.bosemantic.semanticstructure.Filter extractFilter(
			FilterImpl filter) {
		com.bot.botmeta.bosemantic.semanticstructure.Filter boFilter = new com.bot.botmeta.bosemantic.semanticstructure.Filter();
		// RelationalBinding relationalBinding = (RelationalBinding)
		// filter.getBinding();
		StringBuilder path = new StringBuilder();
		String rootName = filter.getName();
		BlContainer parent = filter.getParent();
		path = getPath(parent, rootName, path);
		String attributeName = filter.getName();
		boFilter.setName(attributeName);
		boFilter.setId(filter.getIdentifier());
		return boFilter;
	}

}
