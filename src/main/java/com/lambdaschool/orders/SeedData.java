//package com.lambdaschool.orders;
//
//import com.github.javafaker.Faker;
//import com.lambdaschool.orders.models.Agent;
//import com.lambdaschool.orders.models.Customer;
//import com.lambdaschool.orders.models.Payment;
//import com.lambdaschool.orders.services.AgentServices;
//import com.lambdaschool.orders.services.CustomerServices;
//import com.lambdaschool.orders.services.OrderServices;
//import com.lambdaschool.orders.services.PaymentServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.util.HashSet;
//import java.util.Locale;
//import java.util.Random;
//import java.util.Set;
//
//@Transactional
//@Component
//public class SeedData implements CommandLineRunner
//{
//  @Autowired
//  private AgentServices agentServices;
//
//  @Autowired
//  private CustomerServices customerServices;
//
//  @Autowired
//    private OrderServices orderServices;
//
//  @Autowired
//    private PaymentServices paymentServices;
//
//  private Random random = new Random();
//
//  @Transactional
//  @Override
//    public void run(String[] args)
//  {
//      Payment pay1 = new Payment("Cash");
//      pay1 = paymentServices.save(pay1);
//      Payment pay2 = new Payment("Credit Card");
//      pay2 = paymentServices.save(pay2);
//      Payment pay3 = new Payment("Mobile Pay");
//      pay3 = paymentServices.save(pay3);
//
//      Faker nameFaker = new Faker(new Locale("en-US"));
//
//      Set<String> custNamesSet = new HashSet<>();
//      for (int i = 0; i < 100; i++)
//      {
//          custNamesSet.add(nameFaker.shakespeare());
//      }
//
//      for (String agentName : custNamesSet)
//      {
//          Customer fakeCustomer = new Customer(custName,
//                                        nameFaker.address().cityName(),
//                                        nameFaker.address().city(),
//                                        nameFaker.address().country(),
//                                        nameFaker.numerify(),
//                                        nameFaker.);
//      }
//
//
//  }
//}
