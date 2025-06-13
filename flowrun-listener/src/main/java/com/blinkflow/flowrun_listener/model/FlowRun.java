package com.blinkflow.flowrun_listener.model;

import java.util.Map;
import com.blinkflow.flowrun_listener.config.JsonToMapConverter;
import com.blinkflow.flowrun_listener.model.enums.FlowRunStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowRun {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "TEXT")
	@Convert(converter = JsonToMapConverter.class)
	private Map<String, Object> metadata;
	@ManyToOne()
	@JoinColumn(name = "flow_id", nullable = false)
	private Flow flow;
	@OneToOne(mappedBy = "flowRun")
	private FlowRunOutBox FlowRunOutBox;
	@Enumerated(EnumType.STRING)
	private FlowRunStatus status;
}
