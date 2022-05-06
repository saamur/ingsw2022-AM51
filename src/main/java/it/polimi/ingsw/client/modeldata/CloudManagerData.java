package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.CloudManager;

import java.io.Serializable;
import java.util.Arrays;

public record CloudManagerData(CloudData[] clouds) implements Serializable {
    public static CloudManagerData createCloudManagerData(CloudManager cloudManager){
        Cloud[] clouds = cloudManager.getClouds();
        CloudData[] cloudDatas = new CloudData[clouds.length];
        for(int i=0; i<clouds.length; i++){
            cloudDatas[i] = CloudData.createCloudData(clouds[i], i);
        }
        return new CloudManagerData(cloudDatas);
    }

    @Override
    public String toString() {
        return "CloudManagerData{" +
                "clouds=" + Arrays.toString(clouds) +
                '}';
    }
}
