package com.psalata.ready4s.api_results;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Paweł on 18.06.2016.
 */
public class City implements Serializable {

    private final String CITY_TYPE = "locality";
    private final String COMMUNITY_TYPE = "administrative_area_level_3";
    private final String COUNTY_TYPE = "administrative_area_level_2";
    private final String VOIVODESHIP_TYPE = "administrative_area_level_1";
    private final String COUNTRY_TYPE = "country";

    private String cityName;
    private String communityName;
    private String countyName;
    private String voivodeshipName;
    private String countryName;

    public City(List<AddressResult> results) {
        List<AddressComponent> addressComponents = results.get(0).address_components;

        for (AddressComponent addressComponent : addressComponents) {
            try {
                if (addressComponent.types.get(0).equals(CITY_TYPE)) {
                    cityName = addressComponent.long_name;
                } else if (addressComponent.types.get(0).equals(COMMUNITY_TYPE)) {
                    communityName = addressComponent.long_name;
                } else if (addressComponent.types.get(0).equals(COUNTY_TYPE)) {
                    countyName = addressComponent.long_name;
                } else if (addressComponent.types.get(0).equals(VOIVODESHIP_TYPE)) {
                    voivodeshipName = addressComponent.long_name;
                } else if (addressComponent.types.get(0).equals(COUNTRY_TYPE)) {
                    countryName = addressComponent.long_name;
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCityName() {
        return cityName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getVoivodeshipName() {
        return voivodeshipName;
    }

    public String getCountryName() {
        return countryName;
    }
}
