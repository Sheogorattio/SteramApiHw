package SteramApiHw;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class Currency {
	private String r030;
	private String cc;
	private double rate;

	public String getCode() {
		return r030;
	}

	public String getName() {
		return cc;
	}

	public double getRate() {
		return rate;
	}
}

public class Program {

	public static void main(String[] args) {
		String addressLine =  "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
		var gson = new Gson();
		List<Currency> currencyRates = gson.fromJson(fetchCurrencies(addressLine), new TypeToken<List<Currency>>(){}.getType());
		//StreamAPI
		//| | | | |
		//V V V V V 
		currencyRates.stream().filter(n-> n.getRate()>30).forEach(n-> System.out.println(n.getCode() + " - " 
																						+ n.getName() + " "
																						+ n.getRate()));
		//Ʌ Ʌ Ʌ Ʌ Ʌ
		//| | | | |
		//StreamAPI
	}
	
	public static String fetchCurrencies(String connectionString){
		var responce = new StringBuilder();
		try {
			var url = new URL(connectionString);
			var connection  = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			
			var contentStream= new BufferedReader(new InputStreamReader(connection.getInputStream()));
			for(String inputLine = new String(); inputLine !=null; inputLine = contentStream.readLine()) {
				responce.append(inputLine);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return responce.toString();
	}
}
