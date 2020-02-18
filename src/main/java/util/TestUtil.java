package util;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {

	public static String getValueByJPath(JSONObject responseJSON, String jpath) {

		Object obj = responseJSON;
		for (String s : jpath.split("/"))
			if (!s.isEmpty())
				// for single value the below if part will be executed
				if (!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);

				// for array value the below else-if part will be executed
				else if (s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
			return obj.toString();
	}

}
