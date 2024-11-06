package org.grails.datastore.gorm.mongo

import grails.gorm.annotation.Entity
import grails.mongodb.MongoEntity
import com.mongodb.ReadPreference
import grails.gorm.tests.GormDatastoreSpec
import org.junit.platform.commons.logging.LoggerFactory

class ReadPreferenceSpec extends GormDatastoreSpec {
    List getDomainClasses() {
        [PotatoFarm]
    }

    void "Gets read preference from collection correctly"() {
        when: "Read read concern by default"
        def rp = PotatoFarm.getCollection().getReadPreference()

        then: "It is primary"
        rp == ReadPreference.primary()

        when: "Ask for secondary"
        def rp2 = PotatoFarm.withReadPreference("secondary") {
            PotatoFarm.getCollection().getReadPreference()
        }

        then: "It is secondary"
        rp2 == ReadPreference.secondary()

        when: "After asking for secondary"
        def rp3 = PotatoFarm.getCollection().getReadPreference()

        then: "It returns to primary"
        rp3 == ReadPreference.primary()
    }
}

@Entity
class PotatoFarm implements MongoEntity<PotatoFarm> {
    String id
    String name
    Integer potatoCount
}
