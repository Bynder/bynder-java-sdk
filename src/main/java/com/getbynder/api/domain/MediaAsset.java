package com.getbynder.api.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.getbynder.api.util.ApiUtils;
import com.getbynder.api.util.ErrorMessages;

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
    private Thumbnails thumbnails;

    public MediaAsset(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished, final String type, final List<String> propertyOptions, final Thumbnails thumbnails) {
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

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public List<BasicNameValuePair> getFieldsNameValuePairs() throws IllegalArgumentException, IllegalAccessException {

        List<BasicNameValuePair> params = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field : fields) {

            if(field.get(this) != null && !Arrays.asList("serialVersionUID", "id", "type", "propertyOptions", "thumbnails").contains(field.getName())) {

                if(field.getName().equals("datePublished")) {
                    if (ApiUtils.isDateFormatValid(field.get(this).toString())) {
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
                    result.append(", ");
                } else {
                    isFirstField = false;
                }

                result.append(field.getName());
                result.append("=");
                result.append(field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append("]");

        return result.toString();
    }

    public class Thumbnails {

        private String thul;
        private String mini;
        private String webimage;

        public String getMini() {
            return mini;
        }

        public void setMini(final String mini) {
            this.mini = mini;
        }

        public String getThul() {
            return thul;
        }

        public void setThul(final String thul) {
            this.thul = thul;
        }

        public String getWebimage() {
            return webimage;
        }

        public void setWebimage(final String webimage) {
            this.webimage = webimage;
        }

        @Override
        public String toString() {

            StringBuilder result = new StringBuilder("[");

            Field[] fields = this.getClass().getDeclaredFields();

            boolean isFirstField = true;

            for (Field field : fields) {
                try {
                    if(!isFirstField) {
                        result.append(", ");
                    } else {
                        isFirstField = false;
                    }

                    result.append(field.getName());
                    result.append("=");
                    result.append(field.get(this));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            result.append("]");

            return result.toString();
        }
    }

}
