package org.torento.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.torento.dto.SaveProductProfileDto;
import org.torento.handler.SaveProductProfileHandler;

import javax.inject.Inject;
import java.util.List;


@Controller("/torento/assignment")
public class SaveProductProfile {

    @Inject
    private SaveProductProfileHandler saveProductProfileHandler;

    @Post(value = "/product/profile")
    public HttpResponse<String> addProductProfile(@Body List<SaveProductProfileDto> listSaveProductProfileDto) {
        final String productProcessId = saveProductProfileHandler.addProductProfile(listSaveProductProfileDto);
        if(productProcessId.equalsIgnoreCase("No Profile found")){
            return HttpResponse.status(HttpStatus.NO_CONTENT,productProcessId);
        }
        return HttpResponse.status(HttpStatus.OK,productProcessId);
    }
}
