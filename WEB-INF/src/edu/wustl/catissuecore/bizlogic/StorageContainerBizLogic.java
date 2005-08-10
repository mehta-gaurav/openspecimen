/**
 * <p>Title: StorageContainerHDAO Class>
 * <p>Description:	StorageContainerHDAO is used to add Storage Container information into the database using Hibernate.</p>
 * Copyright:    Copyright (c) year
 * Company: Washington University, School of Medicine, St. Louis.
 * @author Aniruddha Phadnis
 * @version 1.00
 * Created on Jul 23, 2005
 */

package edu.wustl.catissuecore.bizlogic;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.hibernate.HibernateException;
import edu.wustl.catissuecore.dao.AbstractDAO;
import edu.wustl.catissuecore.dao.DAO;
import edu.wustl.catissuecore.dao.DAOFactory;
import edu.wustl.catissuecore.domain.Site;
import edu.wustl.catissuecore.domain.StorageContainer;
import edu.wustl.catissuecore.domain.StorageContainerDetails;
import edu.wustl.catissuecore.domain.StorageType;
import edu.wustl.catissuecore.storage.StorageContainerTreeNode;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.common.util.dbManager.DAOException;

/**
 * StorageContainerHDAO is used to add Storage Container information into the database using Hibernate.
 * @author aniruddha_phadnis
 */
public class StorageContainerBizLogic extends DefaultBizLogic implements TreeDataInterface
{

    /**
     * Saves the storageContainer object in the database.
     * @param session The session in which the object is saved.
     * @param obj The storageType object to be saved.
     * @throws HibernateException Exception thrown during hibernate operations.
     * @throws DAOException 
     */
	protected void insert(DAO dao, Object obj) throws DAOException 
	{
		StorageContainer container = (StorageContainer)obj;
        
        boolean fullStatus[][] = getStorageContainerFullStatus(container
                .getParentContainer().getSystemIdentifier());

        int noOfContainers = container.getNoOfContainers().intValue();
        
        List list = retrieve(StorageType.class.getName(),
                "systemIdentifier", container.getStorageType()
                        .getSystemIdentifier());
        if (list.size() != 0)
        {
            StorageType type = (StorageType) list.get(0);
            container.setStorageType(type);
        }

        if (container.getSite() != null)
        {
            list = retrieve(Site.class.getName(), "systemIdentifier",
                    container.getSite().getSystemIdentifier());
            if (list.size() != 0)
            {
                Site site = (Site) list.get(0);
                container.setSite(site);
            }
        }

        if (container.getParentContainer() != null)
        {
            list = retrieve(StorageContainer.class.getName(),
                    "systemIdentifier", container.getParentContainer().getSystemIdentifier());
            if (list.size() != 0)
            {
                StorageContainer pc = (StorageContainer) list.get(0);
                container.setParentContainer(pc);
            }
        }

        int posOneCapacity = container.getParentContainer().getStorageContainerCapacity().getOneDimensionCapacity().intValue();
        int posTwoCapacity = container.getParentContainer().getStorageContainerCapacity().getTwoDimensionCapacity().intValue();
        
        int positionDimensionOne = container.getPositionDimensionOne()
                .intValue(), positionDimensionTwo = container
                .getPositionDimensionTwo().intValue();
        
        for (int i = 0; i < noOfContainers; i++)
        {
            StorageContainer cont = new StorageContainer(container);
            cont.setPositionDimensionOne(new Integer(positionDimensionOne));
            cont.setPositionDimensionTwo(new Integer(positionDimensionTwo));
            cont.setName(String.valueOf(i + container.getStartNo().intValue()));
            dao.insert(cont.getStorageContainerCapacity(),true);
            dao.insert(cont,true);

            Collection storageContainerDetailsCollection = cont
                    .getStorageContainerDetailsCollection();
            if (storageContainerDetailsCollection.size() > 0)
            {
                Iterator it = storageContainerDetailsCollection.iterator();
                while (it.hasNext())
                {
                    StorageContainerDetails storageContainerDetails = (StorageContainerDetails) it
                            .next();
                    storageContainerDetails.setStorageContainer(cont);
                    dao.insert(storageContainerDetails,true);
                }
            }

            do
            {
                if (positionDimensionTwo == (posTwoCapacity - 1))
                {
                    positionDimensionOne = (positionDimensionOne + 1) % posOneCapacity;
                }
                positionDimensionTwo = (positionDimensionTwo + 1) % posTwoCapacity;
            }
            while (fullStatus[positionDimensionOne][positionDimensionTwo] != false);
        }
    }

    /**
     * Updates the persistent object in the database.
     * @param session The session in which the object is saved.
     * @param obj The object to be updated.
     * @throws HibernateException Exception thrown during hibernate operations.
     * @throws DAOException 
     */
	protected void update(DAO dao, Object obj) throws DAOException
    {
    }

    public int getNextContainerNumber(long parentID, long typeID,
            boolean isInSite) throws DAOException
    {
        String sourceObjectName = "CATISSUE_STORAGE_CONTAINER";
        String[] selectColumnName = {"max(NAME) as MAX_NAME"};
        String[] whereColumnName = new String[2];

        whereColumnName[0] = "STORAGE_TYPE_ID";
        if (isInSite)
            whereColumnName[1] = "SITE_ID";
        else
            whereColumnName[1] = "PARENT_CONTAINER_ID";

        String[] whereColumnCondition = {"=", "="};
        Object[] whereColumnValue = {Long.toString(typeID),
                Long.toString(parentID)};
        String joinCondition = Constants.AND_JOIN_CONDITION;

        AbstractDAO dao = DAOFactory.getDAO(Constants.JDBC_DAO);

        dao.openSession();

        List list = dao.retrieve(sourceObjectName, selectColumnName,
                whereColumnName, whereColumnCondition, whereColumnValue,
                joinCondition);

        if (!list.isEmpty())
        {
            Object obj = list.get(0);
            if (obj != null)
            {
                String str = (String) obj;
                int no = Integer.parseInt(str);
                return no + 1;
            }
        }

        return 1;
    }

    public Vector getTreeViewData() throws DAOException
    {
        AbstractDAO dao = DAOFactory.getDAO(Constants.HIBERNATE_DAO);
        dao.openSession();

        List list = (List) dao.retrieve(StorageContainer.class.getName());

        dao.closeSession();
        Vector vector = new Vector();
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                StorageContainer storageContainer = (StorageContainer) list.get(i);
                StorageContainerTreeNode treeNode = new StorageContainerTreeNode();
                treeNode.setStorageContainerIdentifier(storageContainer
                        .getSystemIdentifier());
                treeNode.setStorageContainerName(storageContainer.getName());
                treeNode.setStorageContainerType(storageContainer
                        .getStorageType().getType());
                if (storageContainer.getParentContainer() != null)
                {
                    treeNode.setParentStorageContainerIdentifier(storageContainer
                                    .getParentContainer().getSystemIdentifier());
                }
                if (storageContainer.getSite() != null)
                {
                    Site site = storageContainer.getSite();
                    treeNode.setSiteSystemIdentifier(site.getSystemIdentifier());
                    treeNode.setSiteName(site.getName());
                    treeNode.setSiteType(site.getType());
                }

                vector.add(treeNode);
            }
        }

        return vector;
    }

    public boolean[][] getStorageContainerFullStatus(Long systemIdentifier)
            throws DAOException
    {
        List list = retrieve(StorageContainer.class.getName(),
                "systemIdentifier", systemIdentifier);
        boolean[][] fullStatus = null;

        if (!list.isEmpty())
        {
            StorageContainer storageContainer = (StorageContainer) list.get(0);
            Integer oneDimensionCapacity = storageContainer
                    .getStorageContainerCapacity().getOneDimensionCapacity();
            Integer twoDimensionCapacity = storageContainer
                    .getStorageContainerCapacity().getTwoDimensionCapacity();
            fullStatus = new boolean[oneDimensionCapacity.intValue()][twoDimensionCapacity
                    .intValue()];

            if (storageContainer.getChildrenContainerCollection() != null)
            {
                Iterator iterator = storageContainer
                        .getChildrenContainerCollection().iterator();
                while (iterator.hasNext())
                {
                    StorageContainer childStorageContainer = (StorageContainer) iterator
                            .next();
                    Integer positionDimensionOne = childStorageContainer
                            .getPositionDimensionOne();
                    Integer positionDimensionTwo = childStorageContainer
                            .getPositionDimensionTwo();
                    fullStatus[positionDimensionOne.intValue()][positionDimensionTwo
                            .intValue()] = true;
                }
            }
        }
        return fullStatus;
    }
}