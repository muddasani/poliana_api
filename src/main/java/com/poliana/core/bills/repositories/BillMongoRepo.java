package com.poliana.core.bills.repositories;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import com.poliana.core.bills.govtrack.AmendmentGt;
import com.poliana.core.bills.govtrack.bills.BillGt;
import com.poliana.core.bills.govtrack.votes.VoteGt;
import com.poliana.core.bills.entities.Bill;
import com.poliana.core.votes.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Repository
public class BillMongoRepo {

    @Autowired
    private Datastore mongoStore;

    public Vote voteByVoteId(String voteId) {
        Query<Vote> voteQuery =
                mongoStore.find(Vote.class,"voteId",voteId);
        return voteQuery.get();
    }

    public Iterator<VoteGt> govtrackVotesByCongress(int congress) {
        Query<VoteGt> query =
                mongoStore.find(VoteGt.class, "congress", congress);
        return query.iterator();
    }

    public Iterator<BillGt> govtrackBillsByCongress(int congress) {
        Query<BillGt> query =
                mongoStore.find(BillGt.class, "congress", congress+"");
        return query.iterator();
    }

    public Iterator<AmendmentGt> govtrackAmendmentsByCongress(int congress) {
        Query<AmendmentGt> query =
                mongoStore.find(AmendmentGt.class, "congress", congress);
        return query.iterator();
    }

    public Bill billByBillId(String billId) {
        Query<Bill> billQuery =
                mongoStore.find(Bill.class,"billId",billId);
        return billQuery.get();
    }

    public Key<Vote> saveVote(Vote vote) {
        return mongoStore.save(vote);
    }

    public Iterable<Key<Bill>> saveBills(List<Bill> bills) {
        return mongoStore.save(bills);
    }

    public Iterable<Key<Vote>> saveVotes(List<Vote> votes) {
        return mongoStore.save(votes);
    }

    public UpdateResults<Bill> updateBill(Bill bill, String fieldExpr, Object value) {
        UpdateOperations<Bill> billUpdate =
                mongoStore.createUpdateOperations(Bill.class).set(fieldExpr,value);
        return mongoStore.update(bill,billUpdate);
    }

    public Key<Bill> saveBill(Bill bill) {
        return mongoStore.save(bill);
    }
}
