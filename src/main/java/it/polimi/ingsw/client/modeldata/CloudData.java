package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.clouds.Cloud;

import java.io.Serializable;
import java.util.Map;

/**
 * contains all the data of a cloud
 * @param students students on the cloud
 * @param picked boolean that says if the card has already been picked or not
 * @param cloudIndex index of the cloud
 */

public record CloudData(Map<Clan, Integer> students,
                        boolean picked, int cloudIndex) implements Serializable {
    public static CloudData createCloudData(Cloud cloud, int cloudIndex){
        return new CloudData(cloud.getStudents(), cloud.isPicked(), cloudIndex);
    }

    @Override
    public String toString() {
        return "CloudData{" +
                "students=" + students +
                ", picked=" + picked +
                ", cloudIndex=" + cloudIndex +
                '}';
    }

}
