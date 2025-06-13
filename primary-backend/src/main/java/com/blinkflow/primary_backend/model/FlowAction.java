package com.blinkflow.primary_backend.model;

import java.util.Map;
import com.blinkflow.primary_backend.config.JsonToMapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FlowAction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "flow_id")
	private Flow flow;
	@Column(columnDefinition = "TEXT")
	@Convert(converter = JsonToMapConverter.class)
    private Map<String, Object> metadata;
	@ManyToOne
	@JoinColumn(name = "available_action_id", nullable = false)
	private AvailableAction actionType;
	private Integer sortingOrder;
}
