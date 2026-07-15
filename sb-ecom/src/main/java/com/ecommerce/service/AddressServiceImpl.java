package com.ecommerce.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.payload.AddressDTO;
import com.ecommerce.repositories.AddressRepository;
import com.ecommerce.repositories.UserRepository;

@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	public AddressDTO createAddress(AddressDTO addressDTO, User user) {
		Address address = modelMapper.map(addressDTO, Address.class);
		List<Address> addressList = user.getAddresses();
		addressList.add(address);
		user.setAddresses(addressList);
		address.setUser(user);
		Address savedAddress = addressRepository.save(address);
		return modelMapper.map(savedAddress, AddressDTO.class);
	}
	@Override
	public List<AddressDTO> getAddress() {
		List<Address> addresses = addressRepository.findAll();
		List<AddressDTO> addressDTOs=addresses.stream()
					.map(address ->modelMapper.map(address, AddressDTO.class))
					.toList();
		return addressDTOs;
	}
	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address","addressId",addressId));
		return modelMapper.map(address, AddressDTO.class);
	}
	@Override
	public List<AddressDTO> getUserAddress(User user) {
		List<Address> addresses = user.getAddresses();
		List<AddressDTO> addressDTOs=addresses.stream()
					.map(address ->modelMapper.map(address, AddressDTO.class))
					.toList();
		return addressDTOs;
		
	}
	
	@Override
	public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
		Address addressFromDatabase = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address","addressId",addressId));
		addressFromDatabase.setCity(addressDTO.getCity());
		addressFromDatabase.setPincode(addressDTO.getPincode());
		addressFromDatabase.setState(addressDTO.getState());
		addressFromDatabase.setCountry(addressDTO.getCountry());
		addressFromDatabase.setStreet(addressDTO.getStreet());
		addressFromDatabase.setBuildingName(addressDTO.getBuildingName());
		Address updatedAddress = addressRepository.save(addressFromDatabase);
		User user = addressFromDatabase.getUser();
		user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
		user.getAddresses().add(updatedAddress);
		userRepository.save(user);
		return modelMapper.map(updatedAddress, AddressDTO.class);
	}
	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDatabase = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address","addressId",addressId));
		User user = addressFromDatabase.getUser();
		user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
		userRepository.save(user);
		addressRepository.delete(addressFromDatabase);
		return "Address deleted successfully with addressId : " + addressId;
	}

}
