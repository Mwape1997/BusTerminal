package at.ac.fhcampuswien.bean;

public class LoginBean {
    private String svnr;
    private String birthDate;
    private String customerNumber;
    private String licenseNumber;
    private String userPosition;
    private String angestelltennummer;


    public String getSvnr() {
        return svnr;
    }

    public void setSvnr(String svnr) {
        this.svnr = svnr;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getAngestelltennummer() {
        return angestelltennummer;
    }

    public void setAngestelltennummer(String angestelltennummer) {
        this.angestelltennummer = angestelltennummer;
    }

}
