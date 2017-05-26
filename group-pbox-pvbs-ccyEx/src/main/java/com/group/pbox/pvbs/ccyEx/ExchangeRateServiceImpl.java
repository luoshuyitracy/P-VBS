package com.group.pbox.pvbs.ccyEx;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateReqModel;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateRespData;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateRespModel;
import com.group.pbox.pvbs.model.currencyRate.CurrencyRate;
import com.group.pbox.pvbs.persist.ccyEx.CcyExchangeRateMapper;
import com.group.pbox.pvbs.util.ErrorCode;

@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {

	@Resource
	CcyExchangeRateMapper ccyExchangeRateMapper;
	
	
	public CcyExchangeRateRespModel getAllCcyExchangeRate() {
		CcyExchangeRateRespModel ccyExchangeRateRespModel=new CcyExchangeRateRespModel();

		List<CcyExchangeRateRespData> listData = new ArrayList<CcyExchangeRateRespData>();


		List<CurrencyRate> list = ccyExchangeRateMapper.getAllExchangeRate();

		if (list.size() > 0) {
			for (CurrencyRate currencyRate : list) {
				CcyExchangeRateRespData data = new CcyExchangeRateRespData();

				data.setId(currencyRate.getId());
				data.setCurrencyCode(currencyRate.getCurrencyCode());
				data.setExchangeRate(currencyRate.getExchangeRate());

				listData.add(data);	
			}

			ccyExchangeRateRespModel.setListData(listData);
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);			
		}
		else {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);	
		}

		return ccyExchangeRateRespModel;
	}


	public CcyExchangeRateRespModel getCcyExRateByCurrCode(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception {
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();
		List<String> errorList = new ArrayList<String>();
		List<CcyExchangeRateRespData> listData = new ArrayList<CcyExchangeRateRespData>();
		String currencyCode = ccyExchangeRateReqModel.getCurrencyCode();
		CurrencyRate result = ccyExchangeRateMapper.getCcyExRateByCurrCode(currencyCode);
		
		if(result != null){
			CcyExchangeRateRespData ccyExchangeRateRespData = new CcyExchangeRateRespData();
			ccyExchangeRateRespData.setId(result.getId());
			ccyExchangeRateRespData.setCurrencyCode(result.getCurrencyCode());
			ccyExchangeRateRespData.setExchangeRate(result.getExchangeRate());
			listData.add(ccyExchangeRateRespData);
			ccyExchangeRateRespModel.setListData(listData);
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
		}
		else{
			ccyExchangeRateRespModel.setResult(ErrorCode.RECORD_NOT_FOUND);
		}
		return ccyExchangeRateRespModel;
	}

}
