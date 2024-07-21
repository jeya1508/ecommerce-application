package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.entity.DBSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {
    @Autowired
    private MongoOperations mongoOperations;
    public int getSequenceNumber(String seqName)
    {
        //get Sequence Number
        Query query= new Query(Criteria.where("id").is(seqName));
        //Update the sequence
        Update update = new Update().inc("seqNumber",1);

        DBSequence counter = mongoOperations
                .findAndModify(query,
                        update,options().returnNew(true).upsert(true),
                        DBSequence.class
                );
        return !Objects.isNull(counter)?counter.getSeqNumber():1;

    }
}
