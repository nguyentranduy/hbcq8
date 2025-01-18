package com.hbc.dto.googledrive;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GoogleDriveFileDTO implements Serializable {

	private static final long serialVersionUID = 4959000224225931083L;

	private String id;
	private String name;
	private String link;
	private String size;
	private String thumbnailLink;
	private boolean shared;
}
