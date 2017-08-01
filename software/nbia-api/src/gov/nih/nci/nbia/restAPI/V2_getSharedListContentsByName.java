//To Test: http://localhost:8080/nbia-api/api/v1/getSeries
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&PatientID=1.3.6.1.4.1.9328.50.1.0001&format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?PatientID=1.3.6.1.4.1.9328.50.1.0001&format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?Collection=RIDER Pilot&format=csv
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=xml
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=html
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?StudyInstanceUID=1.3.6.1.4.1.9328.50.1.2&format=json
//To Test: http://localhost:8080/nbia-api/api/v1/getSeries?StudyInstanceUID=1.3.6.1.4.1.9328.50.1.3&format=csv

package gov.nih.nci.nbia.restAPI;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;


@Path("/v2/getContentsByName")
public class V2_getSharedListContentsByName extends getData {
	private static final String[] columns={"SeriesInstanceUID"};
	public final static String TEXT_CSV = "text/csv";

	@Context private HttpServletRequest httpRequest;
	/**
	 * This method get a set of Manufacturer filtered by query keys
	 *
	 * @return String - set of series info
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, TEXT_CSV})
	public Response  constructResponse(@QueryParam("name") String name) {
		List<String> authorizedCollections = null;
		if (name == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("A parameter, name is required for this API call.")
					.type(MediaType.APPLICATION_JSON).build();
		}
		List<Object[]> data = getSharedListContents(name);
		return formatResponse("json", data, columns);
	}
}