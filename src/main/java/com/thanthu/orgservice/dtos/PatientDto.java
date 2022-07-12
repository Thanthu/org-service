package com.thanthu.orgservice.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.thanthu.orgservice.validation.groups.OnCreatePatient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

	private Long id;

	private LocalDateTime createdDateTime;

	private LocalDateTime updateDateTime;

	@NotBlank(groups = { OnCreatePatient.class })
	private String firstName;

	@NotBlank(groups = { OnCreatePatient.class })
	private String lastName;

	@NotNull(groups = { OnCreatePatient.class })
	@Past(message = "dob should be past date.")
	private LocalDate dob;

	@NotBlank(groups = { OnCreatePatient.class })
	@Email
	private String email;
	
	@NotNull(groups = { OnCreatePatient.class })
	private Long doctorId;
	
	@NotNull(groups = { OnCreatePatient.class })
	private Long practiceId;

}
