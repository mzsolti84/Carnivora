package hu.progmatic.carnivora.kepkezeles;

import org.springframework.http.MediaType;

public enum KepTipus {
  JPEG(MediaType.IMAGE_JPEG_VALUE);
  public final String mediaType;

  KepTipus(String mediaType) {
    this.mediaType = mediaType;
  }
}
