package com.hasan.thesisProject.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.8.0.
 */
@SuppressWarnings("rawtypes")
@Generated("org.web3j.codegen.SolidityFunctionWrapperGenerator")
public class CacheAudit extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b503360015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610df38061005c5f395ff3fe608060405234801561000f575f5ffd5b506004361061004a575f3560e01c806303e9e6091461004e5780638da5cb5b14610084578063ca267f28146100a2578063d1757bcd146100c0575b5f5ffd5b61006860048036038101906100639190610578565b6100dc565b60405161007b9796959493929190610693565b60405180910390f35b61008c6102f5565b604051610099919061070e565b60405180910390f35b6100aa61031a565b6040516100b79190610727565b60405180910390f35b6100da60048036038101906100d59190610906565b610325565b005b5f5f5f6060805f5f5f80549050881061012a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161012190610a11565b60405180910390fd5b5f5f898154811061013e5761013d610a2f565b5b905f5260205f2090600502019050805f015f9054906101000a900460ff16600381111561016e5761016d610a5c565b5b815f0160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff16825f0160159054906101000a900460ff1660028111156101b5576101b4610a5c565b5b8360010184600201856003015486600401548380546101d390610ab6565b80601f01602080910402602001604051908101604052809291908181526020018280546101ff90610ab6565b801561024a5780601f106102215761010080835404028352916020019161024a565b820191905f5260205f20905b81548152906001019060200180831161022d57829003601f168201915b5050505050935082805461025d90610ab6565b80601f016020809104026020016040519081016040528092919081815260200182805461028990610ab6565b80156102d45780601f106102ab576101008083540402835291602001916102d4565b820191905f5260205f20905b8154815290600101906020018083116102b757829003601f168201915b50505050509250975097509750975097509750975050919395979092949650565b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f5f80549050905090565b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146103b4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103ab90610b30565b60405180910390fd5b5f6040518060e001604052808860038111156103d3576103d2610a5c565b5b81526020018773ffffffffffffffffffffffffffffffffffffffff16815260200186600281111561040757610406610a5c565b5b815260200185815260200184815260200142815260200183815250908060018154018082558091505060019003905f5260205f2090600502015f909190919091505f820151815f015f6101000a81548160ff021916908360038111156104705761046f610a5c565b5b02179055506020820151815f0160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506040820151815f0160156101000a81548160ff021916908360028111156104e5576104e4610a5c565b5b021790555060608201518160010190816104ff9190610cee565b5060808201518160020190816105159190610cee565b5060a0820151816003015560c082015181600401555050505050505050565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b61055781610545565b8114610561575f5ffd5b50565b5f813590506105728161054e565b92915050565b5f6020828403121561058d5761058c61053d565b5b5f61059a84828501610564565b91505092915050565b6105ac81610545565b82525050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6105db826105b2565b9050919050565b6105eb816105d1565b82525050565b5f81519050919050565b5f82825260208201905092915050565b5f5b8381101561062857808201518184015260208101905061060d565b5f8484015250505050565b5f601f19601f8301169050919050565b5f61064d826105f1565b61065781856105fb565b935061066781856020860161060b565b61067081610633565b840191505092915050565b5f819050919050565b61068d8161067b565b82525050565b5f60e0820190506106a65f83018a6105a3565b6106b360208301896105e2565b6106c060408301886105a3565b81810360608301526106d28187610643565b905081810360808301526106e68186610643565b90506106f560a08301856105a3565b61070260c0830184610684565b98975050505050505050565b5f6020820190506107215f8301846105e2565b92915050565b5f60208201905061073a5f8301846105a3565b92915050565b6004811061074c575f5ffd5b50565b5f8135905061075d81610740565b92915050565b61076c816105d1565b8114610776575f5ffd5b50565b5f8135905061078781610763565b92915050565b60038110610799575f5ffd5b50565b5f813590506107aa8161078d565b92915050565b5f5ffd5b5f5ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6107ee82610633565b810181811067ffffffffffffffff8211171561080d5761080c6107b8565b5b80604052505050565b5f61081f610534565b905061082b82826107e5565b919050565b5f67ffffffffffffffff82111561084a576108496107b8565b5b61085382610633565b9050602081019050919050565b828183375f83830152505050565b5f61088061087b84610830565b610816565b90508281526020810184848401111561089c5761089b6107b4565b5b6108a7848285610860565b509392505050565b5f82601f8301126108c3576108c26107b0565b5b81356108d384826020860161086e565b91505092915050565b6108e58161067b565b81146108ef575f5ffd5b50565b5f81359050610900816108dc565b92915050565b5f5f5f5f5f5f60c087890312156109205761091f61053d565b5b5f61092d89828a0161074f565b965050602061093e89828a01610779565b955050604061094f89828a0161079c565b945050606087013567ffffffffffffffff8111156109705761096f610541565b5b61097c89828a016108af565b935050608087013567ffffffffffffffff81111561099d5761099c610541565b5b6109a989828a016108af565b92505060a06109ba89828a016108f2565b9150509295509295509295565b7f496e646578206f7574206f6620626f756e6473000000000000000000000000005f82015250565b5f6109fb6013836105fb565b9150610a06826109c7565b602082019050919050565b5f6020820190508181035f830152610a28816109ef565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602160045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680610acd57607f821691505b602082108103610ae057610adf610a89565b5b50919050565b7f4e6f7420617574686f72697a65640000000000000000000000000000000000005f82015250565b5f610b1a600e836105fb565b9150610b2582610ae6565b602082019050919050565b5f6020820190508181035f830152610b4781610b0e565b9050919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f60088302610baa7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610b6f565b610bb48683610b6f565b95508019841693508086168417925050509392505050565b5f819050919050565b5f610bef610bea610be584610545565b610bcc565b610545565b9050919050565b5f819050919050565b610c0883610bd5565b610c1c610c1482610bf6565b848454610b7b565b825550505050565b5f5f905090565b610c33610c24565b610c3e818484610bff565b505050565b5b81811015610c6157610c565f82610c2b565b600181019050610c44565b5050565b601f821115610ca657610c7781610b4e565b610c8084610b60565b81016020851015610c8f578190505b610ca3610c9b85610b60565b830182610c43565b50505b505050565b5f82821c905092915050565b5f610cc65f1984600802610cab565b1980831691505092915050565b5f610cde8383610cb7565b9150826002028217905092915050565b610cf7826105f1565b67ffffffffffffffff811115610d1057610d0f6107b8565b5b610d1a8254610ab6565b610d25828285610c65565b5f60209050601f831160018114610d56575f8415610d44578287015190505b610d4e8582610cd3565b865550610db5565b601f198416610d6486610b4e565b5f5b82811015610d8b57848901518255600182019150602085019450602081019050610d66565b86831015610da85784890151610da4601f891682610cb7565b8355505b6001600288020188555050505b50505050505056fea26469706673582212206e181bd7136a56b800f297c318afaf00e74350e309344673f6a031675394bc3f64736f6c634300081f0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_LOGEVENT = "logEvent";

    public static final String FUNC_GETRECORD = "getRecord";

    public static final String FUNC_GETRECORDCOUNT = "getRecordCount";

    public static final String FUNC_OWNER = "owner";

    @Deprecated
    protected CacheAudit(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CacheAudit(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CacheAudit(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CacheAudit(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> logEvent(BigInteger _operationType, String _actor,
            BigInteger _role, String _resourceId, String _decision, byte[] _hashValue) {
        final Function function = new Function(
                FUNC_LOGEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_operationType), 
                new org.web3j.abi.datatypes.Address(160, _actor), 
                new org.web3j.abi.datatypes.generated.Uint8(_role), 
                new org.web3j.abi.datatypes.Utf8String(_resourceId), 
                new org.web3j.abi.datatypes.Utf8String(_decision), 
                new org.web3j.abi.datatypes.generated.Bytes32(_hashValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, BigInteger, String, String, BigInteger, byte[]>> getRecord(
            BigInteger index) {
        final Function function = new Function(FUNC_GETRECORD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, BigInteger, String, String, BigInteger, byte[]>>(function,
                new Callable<Tuple7<BigInteger, String, BigInteger, String, String, BigInteger, byte[]>>() {
                    @Override
                    public Tuple7<BigInteger, String, BigInteger, String, String, BigInteger, byte[]> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, BigInteger, String, String, BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (byte[]) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getRecordCount() {
        final Function function = new Function(FUNC_GETRECORDCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static CacheAudit load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new CacheAudit(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CacheAudit load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CacheAudit(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CacheAudit load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new CacheAudit(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CacheAudit load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CacheAudit(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CacheAudit> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CacheAudit.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<CacheAudit> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CacheAudit.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<CacheAudit> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CacheAudit.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<CacheAudit> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CacheAudit.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
