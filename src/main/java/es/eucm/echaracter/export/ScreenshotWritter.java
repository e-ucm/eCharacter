/*******************************************************************************
 * <eCharacter> is a research project of the <e-UCM>
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
 *          For more info please visit:  <http://echaracter.e-ucm.es>, 
 *          <http://e-adventure.e-ucm.es> or <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 *      <eCharacter> is free software: you can 
 *      redistribute it and/or modify it under the terms of the GNU Lesser 
 *      General Public License as published by the Free Software Foundation, 
 *      either version 3 of the License, or (at your option) any later version.
 *  
 *      <eCharacter> is distributed in the hope that it 
 *      will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *      warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *      See the GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <eCharacter>. If not, 
 *      see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.echaracter.export;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public final class ScreenshotWritter extends Thread{
    private String exportPath;
    private String nameAnimationToSave;
    private int xMin, xMax, yMin, yMax;
    private ArrayList<String> listAnimationsName;
    private ZIPWritter zipWritter;
    
    public ScreenshotWritter(String exportPath, String nameAnimationToSave, ArrayList<String> listAnimationsName, 
            ZIPWritter zipWritter, int xMin, int xMax, int yMin, int yMax){
        this.exportPath = exportPath;
        this.nameAnimationToSave = nameAnimationToSave;
        this.listAnimationsName = (ArrayList<String>)listAnimationsName.clone();
        this.zipWritter = zipWritter;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    @Override
    public void run(){
        System.out.println("TAMAÑOOOO "+listAnimationsName.size());
        Iterator<String> it = listAnimationsName.iterator();
        while(it.hasNext()){
            String name = it.next();
            try {
                File temp = new File(exportPath+File.separator+name);
                BufferedImage img = ImageIO.read(temp);
                //temp.delete();
                BufferedImage biCut = img.getSubimage(xMin, yMin, xMax-xMin, yMax-yMin);
                sleep(80);
                ImageIO.write(biCut, "png", temp);
                zipWritter.saveFile(temp, name);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScreenshotWritter.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException e) {
                listAnimationsName.remove(name);
                System.out.println("**************** ERROOOOOR  *********************");
                System.out.println("Captura "+name+" no escrita.");
            }
        }
        GenerateAnimation.createAnimation(exportPath, nameAnimationToSave, listAnimationsName, zipWritter);
    }
}