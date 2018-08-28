package review.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "USERMESSAGE")
@NamedQueries({
        @NamedQuery(name = "UserMessage.getAll", query = "select u from UserMessage u"),
        @NamedQuery(name = "UserMessage.getById", query = "select u from UserMessage u where u.id = :id"),
        @NamedQuery(name = "UserMessage.getByAdminBufferId", query = "select u from UserMessage u where u.idAdminBuffer = :idAdminBuffer")
})
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IDADMINBUFFER")
    private Integer idAdminBuffer;

    @Column(name = "ADMINMESSAGE")
    private String adminMessage;

    @Column(name = "ISREAD")
    private boolean isRead;

    public UserMessage() {
    }

    public UserMessage(Integer idAdminBuffer, String adminMessage, boolean isRead) {
        this.idAdminBuffer = idAdminBuffer;
        this.adminMessage = adminMessage;
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAdminBuffer() {
        return idAdminBuffer;
    }

    public void setIdAdminBuffer(Integer idAdminBuffer) {
        this.idAdminBuffer = idAdminBuffer;
    }

    public String getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMessage that = (UserMessage) o;

        if (idAdminBuffer != that.idAdminBuffer) return false;
        return adminMessage != null ? adminMessage.equals(that.adminMessage) : that.adminMessage == null;
    }

    @Override
    public int hashCode() {
        int result = idAdminBuffer;
        result = 31 * result + (adminMessage != null ? adminMessage.hashCode() : 0);
        return result;
    }
}
