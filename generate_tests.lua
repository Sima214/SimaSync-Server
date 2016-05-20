#!/usr/local/bin/lua
--Small file for automatic generation of test files
local dir = "/home/sima/Storage/projects/java/SimaSync-Server".."/test/"
local base_command1 = "dd if=/dev/urandom of="
local base_command2 = " bs=1024 count="
local remaining = 1024 * 256--How many kilobyte of files we want to create totally? Default is 1Gb
local min_count = 1024--Minimum size of all files in kbytes
local max_count = 16368--Maxinum size of all files in kbytes
local total = 0
os.execute("rm "..dir.."*")
while remaining ~= 0 do
  local tmp = math.min(remaining, math.random(min_count, max_count))
  remaining = remaining - tmp
  total = total + 1
  local fln = tostring(total)
  local fill = 3 - #fln
  fln = string.rep("0", fill)..fln
  tmp = base_command1..dir.."test"..fln..".rnd"..base_command2..tmp
  print(tmp)
  os.execute(tmp)
end
print("Created "..total.." files in the end")
