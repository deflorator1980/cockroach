package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RestController
@RibbonClient(name = "say-hello", configuration = JpaRestConfiguration.class)
public class BalancerApplication {

  @LoadBalanced
  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }

  @Autowired
  RestTemplate restTemplate;

  @RequestMapping("/hi")
  public Object hi(@RequestParam(value="name", defaultValue="Artaban") String name) {
    int a = 1;
    return this.restTemplate.getForObject("http://jparest/people", Object.class);
  }

  @RequestMapping("/hi/{id}")
  public Object hiOne(@PathVariable int id) {
    int a = 1;
    return this.restTemplate.getForObject("http://jparest/people/" + id, Object.class);
  }

  @RequestMapping(value = "/hi", method = RequestMethod.POST)
  public Object create(@RequestBody Object person) {
    int a = 1;
    return this.restTemplate.postForObject("http://jparest/people", person, Object.class);
  }

  @RequestMapping(value = "/hi/{id}", method = RequestMethod.DELETE)
  public Object del(@PathVariable int id) {
    int a =1;
    return this.restTemplate.exchange("http://jparest/people/" + id, HttpMethod.DELETE, null, Object.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(BalancerApplication.class, args);
  }
}

