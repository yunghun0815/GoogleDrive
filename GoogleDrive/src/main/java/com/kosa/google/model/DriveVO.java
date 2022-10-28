package com.kosa.google.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class DriveVO {
	
	private String id;
	private int maxCapacity;
	private int useCapacity;
}
