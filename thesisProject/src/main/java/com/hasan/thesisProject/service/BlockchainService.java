package com.hasan.thesisProject.service;

import com.hasan.thesisProject.blockchain.CacheAudit;
import com.hasan.thesisProject.dto.CacheAuditRecordDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class BlockchainService {

    private final Web3j web3j;
    private final Credentials credentials;

    private String contractAddress = "0xAa9bD9d3703f836610A3322Cb791d4C47D1c9aba";


    private CacheAudit contract;

    @PostConstruct
    public void init() {

        contract = CacheAudit.load(
                contractAddress,
                web3j,
                credentials,
                new StaticGasProvider(
                        BigInteger.valueOf(20_000_000_000L),
                        BigInteger.valueOf(3_000_000L)
                )
        );

        System.out.println("Connected to contract: " + contract.getContractAddress());
    }

    // ================= LOG EVENT =================

    public String logProductEvent(
            int operationType,      // 0=READ,1=CREATE,2=UPDATE,3=DELETE
            int role,               // 0=USER,1=ADMIN,2=SUPER_ADMIN
            String resourceId,
            String decision
    ) throws Exception {

        // Create hash from data
        String dataToHash = operationType + resourceId + decision;
        byte[] hashBytes = Hash.sha3(dataToHash.getBytes(StandardCharsets.UTF_8));

        var receipt = contract.logEvent(
                BigInteger.valueOf(operationType),
                credentials.getAddress(),
                BigInteger.valueOf(role),
                resourceId,
                decision,
                hashBytes
        ).send();

        return receipt.getTransactionHash();
    }

    public BigInteger getRecordCount() throws Exception {
        return contract.getRecordCount().send();
    }
    public CacheAuditRecordDTO getRecord(BigInteger index) throws Exception {
        // contract.getRecord(index).send() returns Tuple7
        var result = contract.getRecord(index).send();

        return new CacheAuditRecordDTO(
                result.component1(), // operationType
                result.component2(), // actor address
                result.component3(), // role
                result.component4(), // resourceId
                result.component5(), // decision
                result.component6(), // timestamp
                org.web3j.utils.Numeric.toHexString(result.component7()) // Convert bytes32 to Hex String
        );
    }
}