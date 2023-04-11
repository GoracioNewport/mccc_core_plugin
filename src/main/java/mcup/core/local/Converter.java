package mcup.core.local;

import mcup.core.local.data.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Converter {

  public LinkedHashMap<String, Team> listToMap(List<Team> list) {
    LinkedHashMap<String, Team> returnValue =  new LinkedHashMap<>();

    for (Team team : list) {
      returnValue.put(team.name, team);
    }

    return returnValue;
  }

  public List<Team> mapToList(LinkedHashMap<String, Team> data) {

    List<Team> returnValue = new ArrayList<>();

    for (String key_name : new ArrayList<>(data.keySet()))
      returnValue.add(data.get(key_name));


    return returnValue;
  }

}
