package com.blinkflow.flowrun_producer.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Flow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@OneToOne(mappedBy = "flow", cascade = CascadeType.ALL)
	private FlowTrigger flowTrigger;
	@OneToMany(mappedBy = "flow", cascade = CascadeType.ALL)
	private List<FlowAction> flowActions;
	@OneToMany(mappedBy = "flow", cascade = CascadeType.ALL)
	private List<FlowRun> flowRuns;
	
	
}
