package com.security.demo.event;

import com.security.demo.entity.Someone;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author 李羅
 */
public class OnResetShadowEvent extends ApplicationEvent {

	private static final long serialVersionUID = -526635504185884187L;

	private Someone talent;

	public OnResetShadowEvent(Someone talent) {
		super(talent);
		this.talent = talent;
	}

	public Someone getTalent() {
		return talent;
	}

	public void setTalent(Someone talent) {
		this.talent = talent;
	}

}
