package net.corda.option.flow.client

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.crypto.TransactionSignature
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.identity.Party
import net.corda.core.transactions.FilteredTransaction
import net.corda.core.utilities.unwrap

/** Called by the client to request the oracle's signature over a filtered transaction. */
@InitiatingFlow
class SignTx(private val oracle: Party, private val ftx: FilteredTransaction) : FlowLogic<TransactionSignature>() {
    @Suspendable override fun call(): TransactionSignature {
        val oracleSession = initiateFlow(oracle)
        return oracleSession.sendAndReceive<TransactionSignature>(ftx).unwrap { it }
    }
}