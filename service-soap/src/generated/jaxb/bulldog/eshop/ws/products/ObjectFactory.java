//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.4 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.30 at 11:50:45 AM GMT+03:00 
//


package bulldog.eshop.ws.products;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bulldog.eshop.ws.products package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bulldog.eshop.ws.products
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProductByIdRequest }
     * 
     */
    public GetProductByIdRequest createGetProductByIdRequest() {
        return new GetProductByIdRequest();
    }

    /**
     * Create an instance of {@link GetProductByIdResponse }
     * 
     */
    public GetProductByIdResponse createGetProductByIdResponse() {
        return new GetProductByIdResponse();
    }

    /**
     * Create an instance of {@link ProductWS }
     * 
     */
    public ProductWS createProductWS() {
        return new ProductWS();
    }

    /**
     * Create an instance of {@link GetAllProductsRequest }
     * 
     */
    public GetAllProductsRequest createGetAllProductsRequest() {
        return new GetAllProductsRequest();
    }

    /**
     * Create an instance of {@link GetAllProductsResponse }
     * 
     */
    public GetAllProductsResponse createGetAllProductsResponse() {
        return new GetAllProductsResponse();
    }

}