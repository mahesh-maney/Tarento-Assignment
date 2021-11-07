package org.torento.dao;

import io.reactivex.Single;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.torento.handler.SaveProductProfileHandler;
import org.torento.model.ProductProfileProcessMetadata;

import javax.inject.Singleton;

@Singleton
public class SaveProductProfileDao extends AbstractMongoDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveProductProfileHandler.class);

    public Object addProductProfile(final ProductProfileProcessMetadata productProfileProcessMetadata) {
        Query query = Query.query(Criteria.where("productID").is(productProfileProcessMetadata.getProductID()));
        final Document responseDocument = (Document) Single.fromPublisher(getCollection().find(query.getQueryObject())).onErrorReturnItem(new Document()).blockingGet();
        Document toSaveDocument = new Document();
        toSaveDocument.put("productID",productProfileProcessMetadata.getProductID());
        toSaveDocument.put("productName",productProfileProcessMetadata.getProductName());
        toSaveDocument.put("productDescription",productProfileProcessMetadata.getProductDescription());
        toSaveDocument.put("productManufacturer",productProfileProcessMetadata.getProductManufacturer());
        toSaveDocument.put("productWeight",productProfileProcessMetadata.getProductWeight());
        final Single saveResponse = responseDocument.get("productID") == null ?
                Single.fromPublisher(getCollection().insertOne(toSaveDocument)).map(insertOneResult -> toSaveDocument) :
                Single.fromPublisher(getCollection().replaceOne(new Document("productID", productProfileProcessMetadata.getProductID()), toSaveDocument)).map(updateResult -> toSaveDocument);
        return saveResponse.blockingGet();
    }

}
