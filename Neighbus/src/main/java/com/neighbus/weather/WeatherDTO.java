package com.neighbus.weather;

public class WeatherDTO {
	private String city;       // 도시 이름
    private double temp;       // 온도
    private String desc;       // 날씨 설명 (박무, 맑음 등)
    private int humidity;      // 습도
    

    // Getter (Lombok이 있다면 @Data로 대체 가능)
    public String getCity() { return city; }
    public WeatherDTO(String city, double temp, String desc, int humidity) {
		super();
		this.city = city;
		this.temp = temp;
		this.desc = desc;
		this.humidity = humidity;
	}
	public double getTemp() { return temp; }
    public String getDesc() { return desc; }
    public int getHumidity() { return humidity; }
}
