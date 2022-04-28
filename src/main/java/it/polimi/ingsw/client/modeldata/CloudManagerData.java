package it.polimi.ingsw.client.modeldata;

import java.io.Serializable;

public record CloudManagerData(CloudData[] clouds) implements Serializable {
}
