package com.capgemini.parallel.repo;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.capgemini.parallel.beans.Customer;

public class WalletRepoImpl implements WalletRepo{
	private Map<String, Customer> data; 
	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}
	EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("MyPaymentAppJPA");
	EntityManager em = emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	public boolean save(Customer customer) {
		//Customer c=new Customer();
	
		if (customer != null) {
			if (findOne(customer.getMobileNo()) == null) {
				tx.begin();
				em.persist(customer);
				tx.commit();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Customer findOne(String mobileNo) {
		tx.begin();
		Customer customer = em.find(Customer.class, mobileNo);
		tx.commit();
		//System.out.println(customer);
		if (customer != null) {
			return customer;
		} else {
			return null;
		}

}
	@Override
	public Customer update(Customer customer) {
		tx.begin();
		em.persist(em.merge(customer));
		tx.commit();
		return customer;
	}
	}