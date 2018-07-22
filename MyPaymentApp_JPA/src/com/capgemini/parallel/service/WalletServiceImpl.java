package com.capgemini.parallel.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.capgemini.parallel.beans.Customer;
import com.capgemini.parallel.beans.Wallet;
import com.capgemini.parallel.exception.InvalidInputException;
import com.capgemini.parallel.repo.WalletRepo;
import com.capgemini.parallel.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {
	private WalletRepo repo;

	public WalletServiceImpl(Map<String, Customer> data) {
		repo = new WalletRepoImpl(data);
	}

	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() {
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {
		
		Wallet wallet = new Wallet(amount);
		Customer customer = new Customer(name, mobileNo, wallet);
		boolean c1 = repo.save(customer);
		acceptDetails(customer);
		return customer;
		
		
	}
	private void acceptDetails(Customer customer) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String str = customer.getMobileNo();
			if (validatephone(str)) {
				break;
			} else {
				System.out.println("Wrong Phone Number");
				System.out.println("Enter Phone Number again ");
				customer.setMobileNo(sc.next());
			}

		}
	}

	private boolean validatephone(String phone) {

		String pattern = "[9][0-9]{9}";
		if (phone.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	public Customer showBalance(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);
		if (customer != null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		Customer customer1=repo.findOne(sourceMobileNo);
		Customer customer2=repo.findOne(targetMobileNo);
		Wallet wallet1=new Wallet(customer1.getWallet().getBalance().subtract(amount));
		Wallet wallet2=new Wallet(customer2.getWallet().getBalance().add(amount));
		customer1.setWallet(wallet1);
		customer2.setWallet(wallet2);
		return customer1;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);
		BigDecimal bal = customer.getWallet().getBalance().add(amount);
		Wallet wallet = new Wallet(bal);
		customer.setWallet(wallet);
                if(repo.save(customer)){
		return customer;
        }       else{
	        return null;
        }
	
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);
		BigDecimal bal = customer.getWallet().getBalance().subtract(amount);
		Wallet wallet = new Wallet(bal);
		customer.setWallet(wallet);
                if(repo.save(customer)){
		return customer;

	}       else{
	        return null;
}}}