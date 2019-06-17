package com.vendor.service.impl;


import com.vendor.bean.merchant.Merchant;
import com.vendor.bean.merchant.MerchantNumber;
import com.vendor.bean.merchant.MerchantNumberCriteria;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.MerchantNumberMapper;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IMerchantNumberService;
import com.vendor.utils.DBEntityUtils;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class MerchantNumberServiceImpl implements IMerchantNumberService {

    private Log log = LogFactory.getLog(MerchantNumberServiceImpl.class);

    private MerchantNumberMapper merchantNumberDao;
    private IBaseService<MerchantNumber, MerchantNumber> baseService;

    private SqlSessionFactory sqlSessionFactory;

    public MerchantNumberServiceImpl() {
        System.out.println("RoleServiceImpl init");
    }


    @Autowired(required = true)
    public MerchantNumberServiceImpl(MerchantNumberMapper merchantNumberDao
            , DBEntityUtils dbEntityUtils,SqlSessionFactory sqlSessionFactory) {
        this.merchantNumberDao = merchantNumberDao;

        this.sqlSessionFactory = sqlSessionFactory;

        this.baseService = new BaseMyBaticsServiceImpl<>(this.merchantNumberDao, MerchantNumber.class
                , MerchantNumberCriteria.class, dbEntityUtils);
    }


    public IBaseService<MerchantNumber, MerchantNumber> getBaseService() {
        return this.baseService;
    }

    public void initNumber(Short merchantType, Integer numberCnt) {
        List<MerchantNumber> merchantNumberList = new ArrayList<>();
        Set<String> merchantNuberSet = new HashSet<>();

        MerchantNumber queryMerchant = new MerchantNumber();
        queryMerchant.setMerchantType(merchantType);
        ListResponse<MerchantNumber> merchantNumberListResponse = this.baseService.list(queryMerchant, 1, 10000);
        for (MerchantNumber tempMerchantNum : merchantNumberListResponse.getItems()) {
            merchantNuberSet.add(tempMerchantNum.getNumber());
        }

        String numberData = "";
        int curNumber;
        for (int i = 0; i < numberCnt; i++) {
            while (true) {
                curNumber = RandomUtils.generateNumber(7);
                numberData = merchantType + "" + curNumber;
                if (merchantNuberSet.contains(numberData)) {
                    continue;
                } else {
                    break;
                }
            }
            MerchantNumber newMerchantNumber = new MerchantNumber();
            newMerchantNumber.setMerchantType(merchantType);
            newMerchantNumber.setNumber(numberData);
            merchantNumberList.add(newMerchantNumber);
        }

        log.info("initNumber start..");
        long lCur = System.currentTimeMillis();

        this.baseService.batchInsert(merchantNumberList);

        log.info("initNumber end.. tm:" + (System.currentTimeMillis() - lCur));
    }

    @Transactional(/*isolation = Isolation.READ_COMMITTED,*/propagation = Propagation.REQUIRES_NEW)
    public String getUnUsedMerchantNumber(Short merchantType)
    {
    /*    MerchantNumber queryMerchant = new MerchantNumber();
        queryMerchant.setMerchantType(merchantType);
        queryMerchant.setStatus("Y");
        ListResponse<MerchantNumber> merchantNumberListResponse = this.baseService.list(queryMerchant, 1, 1);
        if(merchantNumberListResponse.getItems().size() > 0)
       {
            String unUsedMerchantNumber =   merchantNumberListResponse.getItems().get(0).getNumber();

            MerchantNumber updateMerchant = new MerchantNumber();
            updateMerchant.setStatus("N");
            updateMerchant.setId(merchantNumberListResponse.getItems().get(0).getId());
            MerchantNumber retNumber = this.baseService.update(updateMerchant);
            log.info("getUnUsedMerchantNumber end.. retNumber:" + GsonUtils.ToJson(retNumber, MerchantNumber.class));
            return  unUsedMerchantNumber;
        } */

        MerchantNumber merchantNumber = this.merchantNumberDao.selectUnusedByStatus("Y");
        if(merchantNumber != null)
        {
            String unUsedMerchantNumber =  merchantNumber.getNumber() ;
            MerchantNumber updateMerchant = new MerchantNumber();
            updateMerchant.setStatus("N");
            updateMerchant.setId(merchantNumber.getId());
            MerchantNumber retNumber = this.baseService.update(updateMerchant);
            log.info("getUnUsedMerchantNumber end.. retNumber:" + GsonUtils.ToJson(retNumber, MerchantNumber.class));
            return  unUsedMerchantNumber;
        }
        else
        {
            return  null;
        }
    }


/*    public String getUnUsedMerchantNumber(Short merchantType)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try{
            MerchantNumber merchantNumber =sqlSession.selectOne("selectUnusedByStatus","Y"); //testForUpdate的sql语句为： select * from test_table where status='0' and rownum<1000 order by create_date desc for update
            if(merchantNumber != null)
            {
                String unUsedMerchantNumber =  merchantNumber.getNumber() ;

                MerchantNumber updateMerchant = new MerchantNumber();
                updateMerchant.setStatus("N");
                updateMerchant.setId(merchantNumber.getId());

                int retNumber = sqlSession.update("com.vendor.mapper.MerchantNumberMapper.updateByPrimaryKeySelective",updateMerchant);

                log.info("getUnUsedMerchantNumber end.. retNumber:" + retNumber);
                return  unUsedMerchantNumber;
            }
        }finally{

           // sqlSession.commit(true);
            try {
                sqlSession.getConnection().commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sqlSession.close();
        }
        return null;
    }*/

 /*   public boolean UpdateMerchantNumberToUsed(Long merchantNumberId)
    {
        MerchantNumber updateMerchant = new MerchantNumber();
        updateMerchant.setStatus("N");
        updateMerchant.setId(merchantNumberId);
        MerchantNumber retNumber = this.baseService.update(updateMerchant);
        return  retNumber != null;
    }*/

}
