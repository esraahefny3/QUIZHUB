package com.toptal.quizhub.persistence.jpa;

import com.toptal.quizhub.domain.catalog.DomainFaker;

public class Faker extends net.datafaker.Faker {

    public final DomainFaker domain = new DomainFaker();

    public final EntityFaker entity = new EntityFaker();
}
