package com.orcchg.yandexcontest.core.network.api

import java.lang.RuntimeException

class NetworkRetryFailedException(cause: Throwable) : RuntimeException(cause)
