package mccc.core.local;

import mccc.core.local.data.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Converter {

  public LinkedHashMap<String, Team> list_to_map(List<Team> list) {
    LinkedHashMap<String, Team> return_value =  new LinkedHashMap<>();

    for (Team team : list) {
      return_value.put(team.name, team);
    }

    return return_value;
  }

  public List<Team> map_to_list(LinkedHashMap<String, Team> data) {

    List<Team> return_value = new ArrayList<>();

    for (String key_name : new ArrayList<>(data.keySet()))
      return_value.add(data.get(key_name));


    return return_value;
  }

}
