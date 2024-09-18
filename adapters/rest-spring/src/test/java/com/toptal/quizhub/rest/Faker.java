package com.toptal.quizhub.rest;

import com.toptal.quizhub.domain.catalog.BaseFaker;
import com.toptal.quizhub.domain.catalog.DomainFaker;

public class Faker extends BaseFaker {

    public final DomainFaker domain = new DomainFaker();

    public final RestFaker rest = new RestFaker();
}
