package com.example.order.domain;

import java.util.Objects;

public final class OrderId {
	private final String value;

	private OrderId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static OrderId of(String value) {
		if (!isValid(value))
			throw new IllegalArgumentException("This is not a valid id");
		return new OrderId(value);
	}

	private static boolean isValid(String value) {
		return value.matches("^[a-z0-9A-Z]{10,20}$");
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderId other = (OrderId) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "OrderId [value=" + value + "]";
	}

}
