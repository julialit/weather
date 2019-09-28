

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import static java.util.Collections.*;


/*
1) Собрать номера городов (не менее 10)
2) Зарегистрироваться на сайте и получить APPID
3) Используя заготовку написать программу, собирающую
сведения о температуре в перечисленных городах
4) Вывести названия городов и температуры в них
от более тёплых более холодным
5) Получить названия городов можно из JSON-данных,
для этого придётся дополнить класс Weather
 */
class M {
    double temp_max, temp_min, temp;
    @Override
    public String toString() {
        return "M{" +
                ", temp_max=" + temp_max +
                ", temp_min=" + temp_min +
                ", temp=" + temp +
                '}' ;
    }
}
class Weather  {
    M main;
    String name;
    @Override
    public String toString() {
        return name + "    temperature: "  + (int) (main.temp-273) +"°C";
    }
}
class MyThread extends Thread implements Comparable<MyThread>{
    public MyThread(String url) {
        this.url = url;
        }
        String url;
        Weather weather;
@Override
    public void run() {
        try {
        URL weather_url = new URL(url);
        InputStream stream = (InputStream) weather_url.getContent();
        Gson gson = new Gson();
        weather = gson.fromJson(new InputStreamReader(stream), Weather.class);
        } catch (IOException e) {
        }
        }
        @Override
        public int compareTo(MyThread o) {
            return Double.compare(this.weather.main.temp, o.weather.main.temp);
        }

    }
public class Main {
    final static String API_URL = "https://api.openweathermap.org/data/2.5/weather?id=%d&appid=2849b2885290a7aeeb34253f4a0d8e81";

    public static void main(String[] args) throws InterruptedException {

        int[] cities = {2643743, 2944388, 2023469, 4792255, 5368361, 344979, 3067696,3453837, 5856195, 1624725, 503550};
        ArrayList <MyThread> weather_threads = new ArrayList<>();
        for(int i=0; i<cities.length; i++){
            MyThread mt = new MyThread(String.format(API_URL, cities[i]));
            weather_threads.add(mt);
        }
        for (MyThread  th: weather_threads) {
            th.start();
            th.join();
        }
        Collections.sort(weather_threads);

        for (MyThread  th: weather_threads) {
            System.out.println(th.weather);
        }
    }

}
