package com.decagon.kindredhairapigrp1.utils.WebScrapping;

import com.decagon.kindredhairapigrp1.services.Impl.webscrapper.*;
import com.decagon.kindredhairapigrp1.services.Scrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WebScrapper {

    AlikayNaturalsScrapper alikayNaturalsScrapper;
    BriogeoHairScrapper briogeoHairScrapper;
    GreenCollectionScrapper greenCollectionScrapper;
    CurlSmithScrapper curlSmithScrapper;
    MelaninHairCareScrapper melaninHairCareScrapper;
    MielleOrganicsScrapper mielleOrganicsScrapper;
    AubreyOrganicsScrapper aubreyOrganicsScrapper;
    TheDouxScrapper theDouxScrapper;
    EdenScrapper edenScrapper;
    GirlAndHairScrapper girlAndHairScrapper;
    JaneCarterScrapper janeCarterScrapper;
    PacificaScrapper pacificaScrapper;
    TaliahWaajidScrapper taliahWaajidScrapper;
    MauiMoistureScrapper mauiMoistureScrapper;
    UncleFunkysDaughterScrapper uncleFunkysDaughterScrapper;
    HoneysScrapper honeysScrapper;

    public WebScrapper(AlikayNaturalsScrapper alikayNaturalsScrapper,
                                         BriogeoHairScrapper briogeoHairScrapper,
                                         GreenCollectionScrapper greenCollectionScrapper,
                                         CurlSmithScrapper curlSmithScrapper,
                                         MelaninHairCareScrapper melaninHairCareScrapper,
                                         MielleOrganicsScrapper mielleOrganicsScrapper,
                                         AubreyOrganicsScrapper aubreyOrganicsScrapper,
                                         TheDouxScrapper theDouxScrapper,
                                         EdenScrapper edenScrapper,
                                         GirlAndHairScrapper girlAndHairScrapper,
                                         JaneCarterScrapper janeCarterScrapper,
                                         PacificaScrapper pacificaScrapper,
                                         TaliahWaajidScrapper taliahWaajidScrapper,
                                         MauiMoistureScrapper mauiMoistureScrapper,
                                         UncleFunkysDaughterScrapper uncleFunkysDaughterScrapper,
                                         HoneysScrapper honeysScrapper) {
        this.alikayNaturalsScrapper = alikayNaturalsScrapper;
        this.briogeoHairScrapper = briogeoHairScrapper;
        this.greenCollectionScrapper = greenCollectionScrapper;
        this.curlSmithScrapper = curlSmithScrapper;
        this.melaninHairCareScrapper = melaninHairCareScrapper;
        this.mielleOrganicsScrapper = mielleOrganicsScrapper;
        this.aubreyOrganicsScrapper = aubreyOrganicsScrapper;
        this.theDouxScrapper = theDouxScrapper;
        this.edenScrapper = edenScrapper;
        this.girlAndHairScrapper = girlAndHairScrapper;
        this.janeCarterScrapper = janeCarterScrapper;
        this.pacificaScrapper = pacificaScrapper;
        this.taliahWaajidScrapper = taliahWaajidScrapper;
        this.mauiMoistureScrapper = mauiMoistureScrapper;
        this.uncleFunkysDaughterScrapper = uncleFunkysDaughterScrapper;
        this.honeysScrapper = honeysScrapper;

    }

    public void runApp() {
        int THREADS = 10;
        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(THREADS);
            List<Scrapper> scrappers = new CopyOnWriteArrayList<>();
            scrappers.add(alikayNaturalsScrapper);
            scrappers.add(briogeoHairScrapper);
            scrappers.add(greenCollectionScrapper);
            scrappers.add(curlSmithScrapper);
            scrappers.add(melaninHairCareScrapper);
            scrappers.add(mielleOrganicsScrapper);
            scrappers.add(aubreyOrganicsScrapper);
            scrappers.add(theDouxScrapper);
            scrappers.add(edenScrapper);
            scrappers.add(girlAndHairScrapper);
            scrappers.add(janeCarterScrapper);
            scrappers.add(pacificaScrapper);
            scrappers.add(taliahWaajidScrapper);
            scrappers.add(mauiMoistureScrapper);
            scrappers.add(uncleFunkysDaughterScrapper);
            scrappers.add(honeysScrapper);

            for (Scrapper scrapper : scrappers) {
                executor.execute(scrapper::scrape);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (executor != null) {
                executor.shutdown();
                System.out.println("\nFinished all threads");
            }
        }
    }
}
