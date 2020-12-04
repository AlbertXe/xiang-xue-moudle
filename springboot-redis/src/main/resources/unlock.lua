if redis.call("get",KEYS[1] == ARGS[1] then redis.call("del",KEYS[1]))
else
  return 0
end
