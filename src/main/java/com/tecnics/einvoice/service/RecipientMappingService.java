package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.RecipientActivityRepo;
import com.tecnics.einvoice.Repo.RecipientEmailMappingRepo;
import com.tecnics.einvoice.Repo.RecipientFTPRepo;
import com.tecnics.einvoice.Repo.RecipientGSTNMappingRepo;
import com.tecnics.einvoice.Repo.RecipientMappingRepo;
import com.tecnics.einvoice.Repo.RecipientWebServiceRepo;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.constants.RecipientConstants;
import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.RecipientActivity;
import com.tecnics.einvoice.entity.RecipientEmailMapping;
import com.tecnics.einvoice.entity.RecipientFtpMapping;
import com.tecnics.einvoice.entity.RecipientGstinMapping;
import com.tecnics.einvoice.entity.RecipientMapping;
import com.tecnics.einvoice.entity.RecipientWebserviceMapping;
import com.tecnics.einvoice.response.RecipientMappingResponse;

@Component
public class RecipientMappingService extends BaseService {

	@Autowired
	JdbcTemplate JdbcTemplate;

	@Autowired
	RecipientMappingRepo recipeintMappingRepo;

	@Autowired
	RecipientActivityRepo recipientActivityRepo;

	@Autowired
	RecipientEmailMappingRepo recipientEmailMappingRepo;

	@Autowired
	RecipientWebServiceRepo recipientWebServiceRepo;

	@Autowired
	RecipientFTPRepo recipientFTPRepo;

	@Autowired
	ErrorLogRepo errorLogRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	RecipientGSTNMappingRepo recipientgstRepo;

	public List<RecipientMapping> findAll() {
		return (List<RecipientMapping>) recipeintMappingRepo.findAll();
	}

	public Optional<RecipientMapping> findById(String id) {
		return (Optional<RecipientMapping>) recipeintMappingRepo.findById(id);
	}

	public RecipientMapping update(int id, RecipientMapping obj) {
		return recipeintMappingRepo.save(obj);
	}

	public RecipientMappingResponse save(RecipientMapping obj, String user) {

		int failCount = 0;
		int successCount = 0;
		RecipientMappingResponse respObj = new RecipientMappingResponse();
		try {
			RecipientMapping response = recipeintMappingRepo.save(obj);
			respObj.setRecipientMappingId(response.getRecipientId());
			List<RecipientGstinMapping> gsts = obj.getRecipientGstinMappings();
			for (RecipientGstinMapping gstn : gsts) {
				gstn.setRecipientMapping(obj);
				recipientgstRepo.save(gstn);
			}
			List<RecipientActivity> activities = obj.getRecipientActivities();
			for (RecipientActivity activity : activities) {
				activity.setRecipientMapping(obj);
				activity.setActionBy(user);
				recipientActivityRepo.save(activity);
			}
			// For Detail Tables of Delivery mode
			if (obj.getDeliveryMode() != null && !obj.getDeliveryMode().isEmpty()) {
				if (obj.getDeliveryMode().equalsIgnoreCase("email")) {
					List<RecipientEmailMapping> emailers = obj.getRecipientEmailMappings();
					for (RecipientEmailMapping email : emailers) {
						email.setRecipientMapping(obj);
						recipientEmailMappingRepo.save(email);
						System.out.println("email-done");
					}
				} else if (obj.getDeliveryMode().equalsIgnoreCase(Constants.FTP)) {

					List<RecipientFtpMapping> ftps = obj.getRecipientFtpMappings();
					for (RecipientFtpMapping ftpObj : ftps) {
						ftpObj.setRecipientMapping(obj);
						recipientFTPRepo.save(ftpObj);
					}

				}

				else if (obj.getDeliveryMode().equalsIgnoreCase(Constants.WEBSERVICE)) {

					List<RecipientWebserviceMapping> wsobj = obj.getRecipientWebserviceMappings();
					for (RecipientWebserviceMapping ws : wsobj) {
						ws.setRecipientMapping(obj);
						recipientWebServiceRepo.save(ws);
					}

				}
			}
			successCount++;
		}

		catch (Exception ex) {
			failCount++;
			errorLogRepo.save(new ErrorLog(RecipientConstants.RECIEPIENT_MAPPING, "", getStackTrace(ex),
					RecipientConstants.SAVE_ACTION, "", "Backend", user));
			System.err.println(getStackTrace(ex));
		}

		respObj.setFailureTransactionsCount(failCount);
		respObj.setSuccessTransactionsCount(successCount);
		respObj.setTotalTransactionsCount(failCount + successCount);
		return respObj;
	}

	public void delete(String id) {
		recipeintMappingRepo.deleteById(id);
	}

	public String generateReciepientID() {
		String sql = "select einvoicing.generate_recipient_id() ";
		return jdbcTemplate.queryForObject(sql, new Object[] {}, String.class);

	}

	public Map<String, Object> getGSTNList(String username) {
		String partnerID = getPartnerIdfromUserId(username);
		System.err.println(partnerID);
		List<Map<String, Object>> gstList = jdbcTemplate.queryForList(SQLQueries.GET_GSTN_DETAILS,
				new Object[] { partnerID });
		return gstList.stream()
				.collect(Collectors.toMap(k -> (String) k.get("gstin"), k -> (String) k.get("business_name")));

	}

}
