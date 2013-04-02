/*******************************************************************************
 * <eAdventure Character Configurator> is a research project of the <e-UCM>
 *          research group.
 *
 *    Developed by: Alejandro Muñoz del Rey, Sergio de Luis Nieto and David González
 *    Ledesma.
 *    Under the supervision of Baltasar Fernández-Manjón and Javier Torrente
 * 
 *    Copyright 2012-2013 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://character.e-ucm.es>, 
 *          <http://e-adventure.e-ucm.es> or <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 *      <eAdventure Character Configurator> is free software: you can 
 *      redistribute it and/or modify it under the terms of the GNU Lesser 
 *      General Public License as published by the Free Software Foundation, 
 *      either version 3 of the License, or (at your option) any later version.
 *  
 *      <eAdventure Character Configurator> is distributed in the hope that it 
 *      will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *      warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *      See the GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <eAdventure Character Configurator>. If not, 
 *      see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package control;

import data.family.*;
import data.family.ScaleStageType.BoneController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBElement;
import types.StageType;

public class FamilyControl 
{
    private Family family;
    
    public FamilyControl(Family family)
    {
        this.family = family;
    }
    
    /**************************** STAGES *************************************/
    public ArrayList<String> getStagesLabels()
    {
        ArrayList<String> listStagesLabels = new ArrayList<String>();
        ArrayList<ScaleStageType> listScaleStage = (ArrayList<ScaleStageType>) family.getStages().getScaleStage();
        Iterator<ScaleStageType> it = listScaleStage.iterator();
        while(it.hasNext())
        {
            ScaleStageType sst = it.next();
            listStagesLabels.add(sst.getStageLabel());
        }
        ArrayList<MultiStageType> listMultiStage = (ArrayList<MultiStageType>) family.getStages().getMultiStage();
        Iterator<MultiStageType> it2 = listMultiStage.iterator();
        while(it2.hasNext())
        {
            MultiStageType mst = it2.next();
            listStagesLabels.add(mst.getStageLabel());
        }
        return listStagesLabels;
    }
    
    public ArrayList<StageType> getStagesTypes()
    {
        ArrayList<StageType> listStagesTypes = new ArrayList<StageType>();
        ArrayList<ScaleStageType> listScaleStage = (ArrayList<ScaleStageType>) family.getStages().getScaleStage();
        Iterator<ScaleStageType> it = listScaleStage.iterator();
        while(it.hasNext())
        {
            ScaleStageType sst = it.next();
            listStagesTypes.add(StageType.scaleStage);
        }
        ArrayList<MultiStageType> listMultiStage = (ArrayList<MultiStageType>) family.getStages().getMultiStage();
        Iterator<MultiStageType> it2 = listMultiStage.iterator();
        while(it2.hasNext())
        {
            MultiStageType mst = it2.next();
            if(mst.getMeshSubStageOrTextureSubStage().size() == 1){
                listStagesTypes.add(StageType.singleStage);
            }
            else{
                listStagesTypes.add(StageType.multiStage);                
            }            
        }
        return listStagesTypes;
    }
    
    public int getNumSubStage(String idMultiStage)
    {
        ArrayList<MultiStageType> listMultiStage = (ArrayList<MultiStageType>) family.getStages().getMultiStage();
        Iterator<MultiStageType> it = listMultiStage.iterator();
        while(it.hasNext())
        {
            MultiStageType mst = it.next();
            if(mst.getStageLabel().equals(idMultiStage)){
                return mst.getMeshSubStageOrTextureSubStage().size();
            }
        }
        return -1;       
    }
    
    public ArrayList<String> getSubStagesLabels(String idMultiStage)
    {
        ArrayList<String> listSubStagesLabels = new ArrayList<String>();
        ArrayList<MultiStageType> listMultiStage = (ArrayList<MultiStageType>) family.getStages().getMultiStage();
        Iterator<MultiStageType> it = listMultiStage.iterator();
        while(it.hasNext())
        {
            MultiStageType mst = it.next();
            if(mst.getStageLabel().equals(idMultiStage)){
                ArrayList<JAXBElement<SubStageType>> listSubStages = (ArrayList<JAXBElement<SubStageType>>) mst.getMeshSubStageOrTextureSubStage();
                Iterator<JAXBElement<SubStageType>> it2 = listSubStages.iterator();
                while(it2.hasNext())
                {
                    JAXBElement<SubStageType> subStage = it2.next();
                    listSubStagesLabels.add(subStage.getValue().getSubStageLabel());
                }
            }
        }
        return listSubStagesLabels; 
    }
    /**************************************************************************/
    public HashSet<String> getIdBonesController(String idPanelRef)
    {
        HashSet<String> idBonesController = new HashSet<String>();
        ArrayList<ScaleStageType> listScaleStage = (ArrayList<ScaleStageType>) family.getStages().getScaleStage();
        Iterator<ScaleStageType> it = listScaleStage.iterator();
        while(it.hasNext())
        {
            ScaleStageType s = it.next();
            if(s.getIdPanel().equals(idPanelRef)){
                ArrayList<BoneController> listBoneController = (ArrayList<BoneController>) s.getBoneController();
                Iterator<BoneController> it2 = listBoneController.iterator();
                while(it2.hasNext())
                {
                    BoneController bc = it2.next();
                    idBonesController.add(bc.getIdController());
                }                
            }
        }
        return idBonesController;
    }
    
    
    
    
}
