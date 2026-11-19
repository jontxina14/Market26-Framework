package bean;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import gui.Utils;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("querySalesBean")
@SessionScoped
public class QuerySalesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	BLFacade facadeBL = FacadeBean.getBusinessLogic();

	private String bilaketa = "";
	private List<Sale> salmentak;
	private Sale aukeratua;
	private String mezua;
	private List<String> egoerak = Utils.getStatus();
	private String irudia;

	public String bilatu() {
		try {
			Date today = UtilDate.trim(new Date());
			salmentak = facadeBL.getPublishedSales(bilaketa, today);
			mezua = "";
			if (salmentak.isEmpty()) {
				mezua = "Ez da produkturik aurkitu";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && !e.getMessage().isEmpty()) {
				mezua = e.getMessage();
			} else {
				mezua = e.toString();
			}
		}
		return null;
	}

	public String ikusi(Sale s) {
		this.aukeratua = s;
		this.irudia = kargatuIrudia(s);
		return "EskaintzaIkusi";
	}

	private String kargatuIrudia(Sale s) {
		try {
			String file = s.getFile();
			if (file == null || file.isEmpty()) return null;

			Image img = facadeBL.downloadImage(file);
			if (img == null) return null;

			BufferedImage bimg = (BufferedImage) img;

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bimg, "png", baos);
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	
	public String getEgoeraTestua() {
		if (aukeratua == null) return "";
		return Utils.getStatus(aukeratua.getStatus());
	}

	public String getBilaketa() { return bilaketa; }
	public void setBilaketa(String bilaketa) { this.bilaketa = bilaketa; }

	public List<Sale> getSalmentak() { return salmentak; }

	public Sale getAukeratua() { return aukeratua; }

	public String getMezua() { return mezua; }

	public String getIrudia() { return irudia; }
	
}