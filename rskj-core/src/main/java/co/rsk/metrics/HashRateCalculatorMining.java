/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package co.rsk.metrics;

import co.rsk.util.RskCustomCache;
import org.ethereum.db.BlockStore;
import org.ethereum.db.ByteArrayWrapper;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Arrays;

public class HashRateCalculatorMining extends HashRateCalculator {

    private byte[] coinbaseAddress;

    public HashRateCalculatorMining(BlockStore blockStore, RskCustomCache<ByteArrayWrapper, BlockHeaderElement> headerCache, byte[] coinbaseAddress) {
        super(blockStore, headerCache);
        this.coinbaseAddress = coinbaseAddress;
    }

    @Override
    public BigInteger calculateNodeHashRate(Duration period) {
        return calculateHashRate(this::checkOwnership, period);
    }

    private Boolean checkOwnership(BlockHeaderElement element) {
        return Arrays.equals(element.getBlockHeader().getCoinbase(), coinbaseAddress);
    }

}
