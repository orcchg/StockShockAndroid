package com.orcchg.yandexcontest.base

import java.lang.RuntimeException

class MissingRequiredParamsException(msg: String?) : RuntimeException(msg)
