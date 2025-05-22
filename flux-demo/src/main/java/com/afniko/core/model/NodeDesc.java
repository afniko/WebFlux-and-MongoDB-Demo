package com.afniko.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "node")
public class NodeDesc extends NodeRoot {

    private String description;

    public String getDescription() {
        return description;
    }

    public NodeDesc() {
    }

    public NodeDesc(String id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof NodeDesc)) {
            return false;
        }

        NodeDesc nodeDesc = (NodeDesc) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getDescription(), nodeDesc.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getDescription())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("name", getName())
                .append("description", description)
                .toString();
    }
}
