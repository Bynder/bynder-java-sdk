package com.getbynder.sdk.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.getbynder.sdk.util.ErrorMessages;
import com.getbynder.sdk.util.Utils;

/**
 *
 * @author daniel.sequeira
 */
public class MediaAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String copyright;
    private Boolean archive;
    private String datePublished;
    private String type;
    private List<String> propertyOptions;
    private Map<String, String> thumbnails;
    private List<MediaItem> mediaItems;

    public MediaAsset(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished, final String type, final List<String> propertyOptions, final Map<String, String> thumbnails, final List<MediaItem> mediaItems) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.copyright = copyright;
        this.archive = archive;
        this.datePublished = datePublished;
        this.type = type;
        this.propertyOptions = propertyOptions;
        this.thumbnails = thumbnails;
        this.mediaItems = mediaItems;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(final String copyright) {
        this.copyright = copyright;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(final Boolean archive) {
        this.archive = archive;
    }

    public String getPublicationDate() {
        return datePublished;
    }

    public void setPublicationDate(final String datePublished) {
        this.datePublished = datePublished;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<String> getPropertyOptions() {
        return propertyOptions;
    }

    public void setPropertyOptions(final List<String> propertyOptions) {
        this.propertyOptions = propertyOptions;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public List<BasicNameValuePair> getFieldsNameValuePairs() throws IllegalArgumentException, IllegalAccessException {

        List<BasicNameValuePair> params = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field : fields) {

            if(field.get(this) != null && !Arrays.asList("serialVersionUID", "id", "type", "propertyOptions", "thumbnails").contains(field.getName())) {

                if(field.getName().equals("datePublished")) {
                    if (Utils.isDateFormatValid(field.get(this).toString())) {
                        params.add(new BasicNameValuePair(field.getName(), field.get(this).toString()));
                    } else {
                        throw new IllegalArgumentException(ErrorMessages.INVALID_PUBLICATION_DATETIME_FORMAT);
                    }
                } else {
                    params.add(new BasicNameValuePair(field.getName(), field.get(this).toString()));
                }
            }
        }

        return params;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("MediaAsset [");

        Field[] fields = this.getClass().getDeclaredFields();

        boolean isFirstField = true;

        for (Field field : fields) {
            try {
                if(!isFirstField) {
                    result.append(Utils.STR_COMMA);
                    result.append(Utils.STR_SPACE);
                } else {
                    isFirstField = false;
                }

                result.append(field.getName());
                result.append(Utils.STR_EQUALS);
                result.append(field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append("]");

        return result.toString();
    }

    public class MediaItem {

        private String id;
        private String fileName;
        private String type;
        private String dateCreated;
        private int height;
        private int width;
        private int size;
        private int version;
        private Boolean active;
        private Map<String, String> thumbnails;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(final String fileName) {
            this.fileName = fileName;
        }

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(final String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(final int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(final int width) {
            this.width = width;
        }

        public int getSize() {
            return size;
        }

        public void setSize(final int size) {
            this.size = size;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(final int version) {
            this.version = version;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(final Boolean active) {
            this.active = active;
        }

        public Map<String, String> getThumbnails() {
            return thumbnails;
        }

        @Override
        public String toString() {
            return "MediaItem [id=" + id + ", fileName=" + fileName + ", type=" + type + ", dateCreated=" + dateCreated
                    + ", height=" + height + ", width=" + width + ", size=" + size + ", version=" + version
                    + ", active=" + active + ", thumbnails=" + thumbnails + "]";
        }
    }

}
